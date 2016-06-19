package networking.packets;

/**
 * Contient la configuration d’une manche,
 * c’est à dire quand affiché les chaises et quels chaises afficher
 */
public class RoundInfo extends BasePacket {

    /**
     * Nom du round
     */
    private String roundName;

    /**
     * Date à laquelle il faut affiché les chaises (à la milliseconde)
     */
    private String showChairsAt;

    /**
     * Indexes des chaises à afficher
     */
    private int[] chairs;

    public RoundInfo(){}

    public RoundInfo(String roundName, String showChairsAt, int[] chairs) {
        this.roundName = roundName;
        this.showChairsAt = showChairsAt;
        this.chairs = chairs;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getShowChairsAt() {
        return showChairsAt;
    }

    public void setShowChairsAt(String showChairsAt) {
        this.showChairsAt = showChairsAt;
    }

    public int[] getChairs() {
        return chairs;
    }
}
