package de.IM.Smash.Util;

//  ╔══════════════════════════════════════╗
//  ║      ___       ___                   ║
//  ║     /  /___   /  /(_)____ ____  __   ║
//  ║    /  // __/ /  // // ) // ___// )\  ║                                  
//  ║   /  // /__ /  // //  _/(__  )/ __/  ║                                                                         
//  ║  /__/ \___//__//_//_/  /____/ \___/  ║                                              
//  ╚══════════════════════════════════════╝


import net.minecraft.FileUtils;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Iclipse on 04.06.2021
 */
public class WorldCopy {
    private WorldCopy() {
    }

    public static void copy(File from, File to, boolean playerdata) {
        try {
            if (to.exists()) {
                Files.deleteIfExists(to.toPath());
            }
            to.mkdir();

            for (File file : from.listFiles()) {
                if (file.isDirectory()) {
                    if (!playerdata) {
                        if (file.getName().equals("playerdata") || file.getName().equals("advancements") || file.getName().equals("stats")) {
                            continue;
                        }
                    }
                    Files.copy(file.toPath(), new File(to.getAbsolutePath() + "/" + file.getName()).toPath());
                } else {
                    Files.copy(file.toPath(), new File(to.getAbsolutePath()  + "/" + file.getName()).toPath());
                }
            }
            if(!playerdata){
                new File(to.getAbsolutePath()  + "/" + "playerdata").mkdir();
                new File(to.getAbsolutePath()  + "/" + "advancements").mkdir();
                new File(to.getAbsolutePath()  + "/" + "stats").mkdir();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
