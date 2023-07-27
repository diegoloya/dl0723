package com.toolStore.model;

import java.util.ArrayList;

public class Tools {
    private ArrayList<ToolModel> tools;
    private ArrayList<ToolTypeModel> toolTypes;

    public Tools() {
    }

    public ArrayList<ToolModel> getTools() {
        return tools;
    }

    public void setTools(ArrayList<ToolModel> tools) {
        this.tools = tools;
    }

    public ArrayList<ToolTypeModel> getToolTypes() {
        return toolTypes;
    }

    public void setToolTypes(ArrayList<ToolTypeModel> toolTypes) {
        this.toolTypes = toolTypes;
    }
}
