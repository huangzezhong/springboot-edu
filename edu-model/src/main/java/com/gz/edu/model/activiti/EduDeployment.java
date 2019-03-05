package com.gz.edu.model.activiti;

import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.repository.Deployment;

import java.io.Serializable;

@Setter
@Getter
public class EduDeployment implements Serializable {

    private String name;

    private String id;

    public EduDeployment() {

    }

    public EduDeployment(Deployment deployment) {
        if (deployment != null) {
            this.name = deployment.getName();
            this.id = deployment.getId();
        }
    }

}
