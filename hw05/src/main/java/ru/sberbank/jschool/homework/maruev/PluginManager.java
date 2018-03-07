package ru.sberbank.jschool.homework.maruev;

import ru.sberbank.jschool.homework.classloaders.PluginNotFoundException;

public class PluginManager {

    private final String rootDirectory;
    SpecialClassLoader loader = new SpecialClassLoader();

    public PluginManager(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    /**
     * method takes as a parameter a folder name in the root plugin directory,
     * loads the plugin .class file from the folder if present,
     * and returns a Plugin object
     *
     * @param pluginName - name of the plugin folder
     * @return Plugin
     * @throws PluginNotFoundException - when folder named 'pluginName' is missing,
     *                                 or it contains no .class files
     */
    public Plugin loadPlugin(String pluginName) throws PluginNotFoundException {
        try {
            return (Plugin) loader.loadClass(rootDirectory + "/" + pluginName).newInstance();
        } catch (Exception e) {
            throw new PluginNotFoundException("couldn't locate plugin " + pluginName);
        }
    }
}
