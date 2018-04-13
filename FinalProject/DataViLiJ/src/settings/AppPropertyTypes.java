package settings;

public enum AppPropertyTypes {
    /* path to the icons*/
    GUI_ICON_PATH,

    /*USER INTERFACE ICON FILES*/
    SCREENSHOT_ICON,
    CONFIGURATION_ICON,
    BACK_ICON,
    START_ICON,

    /*TOOLTIPS FOR BUTTONS*/
    SCREENSHOT_TOOLTIP,
    CONFIGURATION_TOOLTIP,

    /* file path symbol*/
    Separator,

    /* application GUI label*/
    DISPLAY_BUTTON_TEXT,
    ALGORITHM_TYPES,
    ALGORITHMS,
    CONTINUOUS_RUN_TEXT,
    CONFIRM_TEXT,
    CANCEL_TEXT,
    MAX_INTERVAL_TEXT,
    ITERATION_INTERVAL_TEXT,
    LOADED_DATA_INFO_TEXT,
    LOADED_FILE_LOCATION_TEXT,


    /* application parameter*/
    CSS_Path,
    Image_File_Ext_With_StarKey,


    /*parameters for saving*/
    DATA_FILE_EXT_DESC,
    DATA_FILE_EXT,
    DATA_RESOURCE_PATH,
    Image_File_Ext,
    Image_File_Ext_Desc,

    /*algorithm types labels*/
    CLUSTERING_TYPE,
    CLASSIFICATION_TYPE,

    /*message titles*/
    SAVE_UNSAVED_WORK_TITLE,
    LOAD_ERROR_TITLE,
    SAVE_ERROR_TITLE,
    Subdir_Not_Found_Title,
    ScreenShot_Error_Title,


    /*message contents*/
    SAVE_UNSAVED_WORK,
    LOAD_IO_ERROR_MESSAGE,
    LOAD_WRONG_FORMAT_MESSAGE,
    SAVE_IO_ERROR_MESSAGE,
    RESOURCE_SUBDIR_NOT_FOUND_MESSAGE,
    ScreenShot_Error_Message,
}
