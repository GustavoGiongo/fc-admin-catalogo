package com.fullcycle.admin.catalogo.application;

import com.fullcycle.admin.catalogo.domain.category.Category;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public abstract class UseCase< IN, OUT> {

    public abstract OUT execute(IN anIn);

}