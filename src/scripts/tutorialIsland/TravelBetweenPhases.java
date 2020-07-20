package scripts.tutorialIsland;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import java.util.Arrays;

public class TravelBetweenPhases {

    public boolean travelFromGuide(Script script) throws InterruptedException {
        script.log("Traveling from Gielinor Guide");
        RS2Object door = script.getObjects().closest("Door");
        script.getCamera().toEntity(door);
        script.sleep(Utils.randomInteractionTime(false));
        script.log("Attempting to walk to door");
        script.getWalking().webWalk(new Position(3103, 3096, 0));
        new ConditionalSleep(10000, 12000) {
            @Override
            public boolean condition() throws InterruptedException {
                return script.myPosition() == new Position(3103, 3096, 0);
            }
        }.sleep();
        script.log("Walked to Survival Expert");
        return true;
    }

    public boolean travelFromSurvivalExpert(Script script) throws InterruptedException {
        Area area = new Area(new Position(3089, 3093, 0), new Position(3088, 3090, 0));
        script.log("Traveling from survival expert");
        while (!area.contains(script.myPosition())) {
            boolean isClosed = doorIsClosed(script.getObjects().closest("Gate"), script);
            RS2Object gate = script.getObjects().closest("Gate");
            if (!isClosed) {
                while (!isClosed) {
                    script.sleep(Utils.boundedInteractionTime(1500, 2000));
                    gate = script.getObjects().closest("Gate");
                    isClosed = doorIsClosed(script.getObjects().closest("Gate"), script);
                }
            }
            script.getWalking().walk(new Position(3090, 3091, 0));
            script.sleep(500);
            gate.interact("Open");
            script.sleep(800);

            if (script.myPlayer().isMoving()) {
                while (script.myPlayer().isMoving()) {
                    script.sleep(Utils.boundedInteractionTime(1500, 2000));
                }
            }
        }
        script.sleep(Utils.boundedInteractionTime(2000, 3000));
        script.log("Walking away from gate");
        script.getWalking().walk(new Position(3079, 3084, 0));
        if (script.myPlayer().isMoving()) {
            while (script.myPlayer().isMoving()) {
                script.sleep(Utils.boundedInteractionTime(1500, 2000));
            }
        }
        while(!TutorialIslandLocations.MASTER_NAVIGATOR.getLocation().contains(script.myPosition())) {
            script.getObjects().closest("Door").interact();
            script.sleep(Utils.boundedInteractionTime(2000, 4000));
        }
        return true;
    }

    public boolean travelFromMasterNavigator(Script script) throws InterruptedException {
        script.log("Traveling from master navigator");
        script.getWalking().webWalk(new Position(3086, 3125, 0));
        return true;
    }

    public boolean travelFromQuestGuide(Script script) throws InterruptedException {
        script.log("Traveling from Quest Guide");
        script.getWalking().webWalk(new Position(3080, 9505, 0));
        return true;
    }

    public boolean travelFromMining(Script script) throws InterruptedException{
        script.log("Traveling from Mining Expert");
        script.getWalking().webWalk(new Position(3104, 9507, 0));
        return true;
    }

    public boolean travelFromCombat(Script script) throws InterruptedException{
        script.log("Traveling from Combat Instructor");
//        script.getWalking().webWalk(new Position(3111, 9525, 0));
//        while (script.myPlayer().isMoving())
//            script.sleep(Utils.randomInteractionTime(false));
//        script.getObjects().closest("Ladder").interact("Climb");
//        script.sleep(Utils.randomInteractionTime(false));
        script.getWalking().webWalk(new Position(3122, 3123, 0));
        return true;
    }

    public boolean travelFromBank(Script script){
        script.getWalking().webWalk(new Position(3125, 3124, 0));
        return true;
    }

    public boolean travelFromAccountGuide(Script script){
        script.getWalking().webWalk(new Position(3126, 3107, 0));
        return true;
    }

    public boolean travelFromBrotherBrace(Script script){
        script.getWalking().webWalk(new Position(3140, 3087, 0));
        return true;
    }

    public boolean doorIsClosed(RS2Object door, Script script) throws InterruptedException {
        try {
            return Arrays.asList(door.getDefinition().getActions()).contains("Open");
        } catch (NullPointerException e){
            script.sleep(Utils.boundedInteractionTime(200, 400));
            return doorIsClosed(door, script);
        }
    }



}