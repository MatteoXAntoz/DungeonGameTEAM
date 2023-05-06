package level.myQuest;

import ecs.entities.Hero;
import starter.Game;

public class MyQuest {


    private boolean agreed;
    private int QuestID;
    private String QuestDescription;
    private String QuestReward;

    private boolean accomplished;

    public boolean isAgreed() {
        return agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }


    public int getQuestID() {
        return QuestID;
    }

    public void setQuestID(int questID) {
        QuestID = questID;
    }

    public void setQuest(int index){
        MyQuestConfig myQuestConfig = MyQuestConfig.getInstance();
        setQuestID(myQuestConfig.questID.get(index));
        setQuestDescription(myQuestConfig.questDescription.get(index));
        setQuestReward(myQuestConfig.questReward.get(index));

    }

    public boolean isAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    public String getQuestDescription() {
        return QuestDescription;
    }

    public void setQuestDescription(String questDescription) {
        QuestDescription = questDescription;
    }

    public String getQuestReward() {
        return QuestReward;
    }

    public void setQuestReward(String questReward) {
        QuestReward = questReward;
    }

}
