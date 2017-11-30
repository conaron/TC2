package util;

public class MenuItem {

    private String value;
    private String icon;
    private String action;
    private String update;

    public MenuItem() {
    }

    public MenuItem(String value, String icon, String action, String update) {
        this.value = value;
        this.icon = icon;
        this.action = action;
        this.update = update;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        if (icon.contains("fa-")) {
            return "fa " + icon;
        }
        return "./img/ico/" + icon + ".png";
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

}
