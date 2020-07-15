package com.zapsoftco.relica.controllers;

public interface DataModelConsumer<T> {
    public void setDataModel(T t);
    public  Class<T> getDataModelClass();
}
