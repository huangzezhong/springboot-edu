package com.gz.webapp.activiti;

import com.gz.edu.service.vacation.impl.LeaveApplicationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(classes = EduApplication.class, initializers = ConfigFileApplicationContextInitializer.class)*/
@Slf4j
public class ActivitiTest {

    //获取流程引擎
    private ProcessEngine processEngine;

    //测试流程key
    private static final String PROCESS_INSTANCE_KEY = "vacation_01";

    //测试流程实例ID
    private static String PROCESS_DEFINITION_ID = null;

    //测试流程执行任务ID
    private static String PROCESS_TASK_ID = null;

    //@Before
    public void createProcessEngines() {
        processEngine = ProcessEngines.getDefaultProcessEngine();
    }


    /**
     * 部署流程
     */
    //@Test
    public void deployMentActiviti() {
        processEngine.getRepositoryService()
                .createDeployment()
                .name("vacationProcess")
                .addClasspathResource("processes/vacationProcess.bpmn")
                .deploy()
        ;
    }


    /**
     * 2：启动流程实例
     */
    //@Test
    public void testStartProcessInstance() {
        processEngine.getRuntimeService()
                .startProcessInstanceByKey(PROCESS_INSTANCE_KEY);  //这个是查看数据库中act_re_procdef表
    }


    /**
     * 查询流程定义
     */
    //@Before
    public void queryProcessdefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .processDefinitionKey(PROCESS_INSTANCE_KEY)
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
        for (ProcessDefinition processDefinition : list) {
            log.info("流程部署id==>>{}，对应数据库表==>>act_re_deployment 中DEPLOYMENT_ID_", processDefinition.getDeploymentId());
            log.info("流程部署名称==>>{}，对应数据库表==>>act_re_procdef 中name", processDefinition.getName());
        }
        ProcessDefinition processDefinition = list.get(0);
        PROCESS_DEFINITION_ID = processDefinition.getId();
        log.info("流程实例id==>>{}，对应数据库表==>>act_re_procdef 中id", PROCESS_DEFINITION_ID);
    }


    /**
     * 查询流程执行任务
     */
    //@Before
    public void queryProcessTask() {
        List<Task> taskList = processEngine.getTaskService()
                .createTaskQuery()
                .processDefinitionId(PROCESS_DEFINITION_ID)
                .active()
                .orderByProcessInstanceId()
                .desc()
                .list();
        Task task = taskList.get(0);
        PROCESS_TASK_ID = task.getId();
        log.info("Task id==>>{}，对应数据库表==>>act_ru_task 中id", PROCESS_TASK_ID);
        log.info("Task 执行名称==>>{}，对应数据库表==>>act_ru_task 中name", task.getName());
        log.info("Task 执行id==>>{}，对应数据库表==>>act_ru_execution 中id", task.getExecutionId());
    }


    /**
     * 处理任务节点
     */
    //@Test
    public void testQingjia() {
        Task task = processEngine.getTaskService()
                .createTaskQuery()
                .taskId(PROCESS_TASK_ID)
                .singleResult();
        log.info("{}完成处理", task.getName());
        processEngine.getTaskService()
                .complete(PROCESS_TASK_ID); //查看act_ru_task表
    }


    /**
     * 测试回退后的重新流转
     */
    //@Test
    public void testRevoke() {
        //启动请假流程实例
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        //查询任务ID
        List<Task> taskList = new LeaveApplicationServiceImpl()
                .queryCurretUserTaskByAssignerr("4");
        Task task = taskList.get(0);
        //设置流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("leaderId", 3);
        //提交请假申请-查看act_ru_task表
        processEngine.getTaskService().complete(task.getId(), variables);
    }

}

