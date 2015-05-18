package com.mat.hyb.school.kgk.sas.model;

import org.brightify.torch.annotation.Entity;
import org.brightify.torch.annotation.Id;

/**
 * @author <a href="mailto:hyblmatous@gmail.com">Matous Hybl</a>
 */
@Entity
public class ClassModel {

    @Id
    public Long id;

    public String name;
}
