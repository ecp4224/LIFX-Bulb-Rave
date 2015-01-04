package me.eddiep.lifxbulb.rave;

import com.github.besherman.lifx.LFXClient;
import com.github.besherman.lifx.LFXLight;
import me.eddiep.lifxbulb.rave.system.LightHolder;
import me.eddiep.lifxbulb.rave.system.WASAPI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static WASAPI wasapi;
    private static List<LightHolder> lightsInRave;
    private static long timeout;


    public static void main(String[] args) {
        System.out.println("=======Welcome to LIFX Rave=======");
        log("Looking for blubs..");

        Scanner scanner = new Scanner(System.in);
        LFXClient client = new LFXClient();
        wasapi = new WASAPI();
        try {
            client.open(true);

            /*if (client.getLights().size() == 0) {
                log("Could not find any bulbs!");
                log("Aborting..");
                return;
            }*/

            log("Done");
            lightsInRave = new ArrayList<>();

            do {
                System.out.println();
                System.out.println("Bulbs Found:");
                int i = 0;
                for (LFXLight light : client.getLights()) {
                    i++;
                    System.out.println("    " + i + ". " + light.getLabel());
                }
                System.out.println("(" + i + " found in total)");
                System.out.println();

                System.out.println("Please type the name of each light\n" +
                        "you would like to include in this rave\n" +
                        "separated by commas.");
                System.out.print("> ");
                String lights = scanner.nextLine();
                System.out.println();
                lightsInRave = parseLights(client.getLights(), lights);

                if (lightsInRave.size() == 0) {
                    System.out.println("!! No lights with those names could be found !!");
                }
            } while (lightsInRave.size() == 0);

            log(lightsInRave.size() + " light" + (lightsInRave.size() > 1 ? "s" : "") + " included in rave");

            do {
                System.out.println("How fast should the lights update? (Recommended value: 30)");
                System.out.print("> ");
                try {
                    timeout = 1000L / Long.parseLong(scanner.nextLine());
                    break;
                } catch (Throwable t) {
                    System.out.println("Invalid value!");
                }
            } while (true);

            log("Loading WASAPI");
            wasapi.loadWrapper();
            log("Done");

            log("Starting rave!");
            Thread thread = new Thread(RAVE_RUNNABLE);
            thread.start();
            System.out.println("!! Type \"stop\" to stop the rave !!");


            while (true) {
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("stop")) {
                    running = false;
                    thread.interrupt();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    private static final SimpleDateFormat LOG_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static void log(String message) {
        System.out.println("[" + LOG_FORMAT.format(new Date()) + "] " + message);
    }

    private static List<LightHolder> parseLights(Iterable<LFXLight> originalList, String list) {
        String[] names = list.split(",");
        ArrayList<LightHolder> toReturn = new ArrayList<>(names.length + 2);
        LFXLight found = null;

        for (String name : names) {
            name = name.trim();
            for (LFXLight light : originalList) {
                if (light.getLabel().equalsIgnoreCase(name)) {
                    found = light;
                    break;
                }
            }

            if (found == null) {
                log("Could not find light with label \"" + name + "\"");
                continue;
            }
            toReturn.add(new LightHolder(found));
        }

        return toReturn;
    }


    private static boolean running = true;
    private static final Runnable RAVE_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            while (running) {
                float[] data = wasapi.getSoundData();
                float num = 0;
                for (int i = 0; i <= 5; i++) {
                    num += data[i];
                }

                num *= (13f / 15f);
                if (num < 0) num = 0;

                for (LightHolder light : lightsInRave) {
                    light.raveWithValue(num);
                }

                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
