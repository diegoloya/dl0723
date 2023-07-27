package com.toolStore.model;

import java.util.ArrayList;

public class ToolList {
    private ArrayList<Tool> tools;
    private ArrayList<ToolType> toolTypes;

    public ToolList() {
    }

    public ArrayList<Tool> getTools() {
        return tools;
    }

    public void setTools(ArrayList<Tool> tools) {
        this.tools = tools;
    }

    public ArrayList<ToolType> getToolTypes() {
        return toolTypes;
    }

    public void setToolTypes(ArrayList<ToolType> toolTypes) {
        this.toolTypes = toolTypes;
    }
}
