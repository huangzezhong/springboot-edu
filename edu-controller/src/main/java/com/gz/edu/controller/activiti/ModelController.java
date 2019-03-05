package com.gz.edu.controller.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gz.edu.model.activiti.EduDeployment;
import com.gz.edu.model.sys.JSONMessage;
import com.gz.edu.service.activiti.ActivitiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class ModelController {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ActivitiService activitiService;

    /**
     * 获取所有模型
     *
     * @return
     */
    @GetMapping
    @RequestMapping("/modelList")
    @RequiresPermissions(value = {"modelList"}, logical = Logical.OR)
    public String modelList(org.springframework.ui.Model model) {
        List<Model> models = repositoryService.createModelQuery().orderByCreateTime().desc().list();
        model.addAttribute("models", models);
        return "activiti/model-list";
    }

    @RequestMapping("/createModel")
    @Transactional
    @RequiresPermissions(value = {"createModel"}, logical = Logical.OR)
    public void newModel(HttpServletResponse response) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);
        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        String id = model.getId();
        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
        response.sendRedirect("/modeler.html?modelId=" + id);
    }

    /**
     * 发布模型为流程定义
     */
    @RequestMapping("/deployProcess/{modelId}")
    @ResponseBody
    @Transactional
    @RequiresPermissions(value = {"deployProcess"}, logical = Logical.OR)
    public JSONMessage deploy(@PathVariable String modelId) throws Exception {
        JSONMessage message = new JSONMessage();
        //获取模型
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (bytes == null) {
            message.setFlag(JSONMessage.FAIL);
            message.setMsg("模型数据为空，请先设计流程并成功保存，再进行发布");
            return message;
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            message.setFlag(JSONMessage.FAIL);
            message.setMsg("数据模型不符要求，请至少设计一条主线流程");
            return message;
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        //更新act_ge_bytearray表原有流程
        EduDeployment eduDeployment = new EduDeployment(deployment);
        //更新xml
        activitiService.updateOldResporityByDeployInfo(eduDeployment);
        //更新png
        eduDeployment.setName(".png");
        activitiService.updateOldResporityByDeployInfo(eduDeployment);
        message.setFlag(JSONMessage.SUCCESS);
        message.setMsg("发布成功");
        return message;
    }

    /**
     * 删除流程模型
     */
    @RequestMapping("/deleteProcess/{modelId}")
    @ResponseBody
    @Transactional
    @RequiresPermissions(value = {"deleteProcess"}, logical = Logical.OR)
    public JSONMessage deleteProcess(@PathVariable String modelId) throws Exception {
        JSONMessage message = new JSONMessage();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteModel(modelId);
        message.setFlag(JSONMessage.SUCCESS);
        message.setMsg("删除成功");
        return message;
    }

}
