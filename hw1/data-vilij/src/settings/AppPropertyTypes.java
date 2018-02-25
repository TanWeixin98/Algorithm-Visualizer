package settings;

/**
 * This enumerable type lists the various application-specific property types listed in the initial set of properties to
 * be loaded from the workspace properties <code>xml</code> file specified by the initialization parameters.
 *
 * @author Ritwik Banerjee
 * @see vilij.settings.InitializationParams
 */
public enum AppPropertyTypes {

    /* resource files and folders */
    DATA_RESOURCE_PATH,

    /* user interface icon file names */
    SCREENSHOT_ICON,

    /* tooltips for user interface buttons */
    SCREENSHOT_TOOLTIP,

    /*warning title*/
    UnSave_Work,
    /* warning messages*/
    EXIT_WHILE_RUNNING_WARNING,
    /* error title*/
    Subdir_Not_Found_Title,
    Display_Error_Title,
    /* error messages */
    RESOURCE_SUBDIR_NOT_FOUND,
    Save_Error_Message,
    Load_Error_Message,


    /* application-specific message titles */
    SAVE_UNSAVED_WORK_TITLE,

    /* application-specific messages */
    SAVE_UNSAVED_WORK,
    SAVE_WORK_NOTIFICATION,
    /* file path symbol*/
    Separator,
    /* application-specific parameters */
    DATA_FILE_EXT,
    DATA_FILE_EXT_DESC,
    TEXT_AREA,
    chart_Title,
    Display_Label,
    Text_Field_Title,
    Data_Label_Title_Font,
    READ_ONLY_LABEL,
}
