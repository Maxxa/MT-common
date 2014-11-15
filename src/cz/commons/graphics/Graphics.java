package cz.commons.graphics;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * Rodicovska trida pro implentace animaci.
 * @author Martin Šára
 * @param <T> typ controlleru
 */
public abstract class Graphics<T> { //TODO
    
    /**
     * Vychozi velikost fontu v systemu respektujici aktualni DPI.
     */
    public static final double DEFAULT_FONT_SIZE = Font.getDefault().getSize();
    /**
     * Konstanta udavajici zvetseni oproti standardnimu DPI.
     */
    public static final double PLATFORM_SCALE = DEFAULT_FONT_SIZE / 12.0;
    /**
     * Vychozi barva pozadi.
     */
    public static final Color BACKGROUND_COLOR = Color.LIGHTYELLOW;

    /**
     * Minimalni trvani animace.
     * Vyuzito pro davkove nacitani vstupu bez animace.
     */
    private static final Duration MIN_DURATION = Duration.ONE;
    /**
     * Standardni trvani jedne animace.
     */
    private static final Duration NORMAL_DURATION = Duration.seconds(1);
    
    /**
     * Reference na ridici tridu.
     * Predavani informaci o dokoncenych animacich.
     */
    private final T control;
    /**
     * Kreslici panel.
     */
    private final Pane pane;
    /**
     * Posouvaci panel.
     * Mel by obsahovat kreslici panel.
     * Slouzi ke zjisteni aktualni pozice.
     */
    private final ScrollPane scrollPane;
    /**
     * Probihajici paralelni animace.
     */
    private ParallelTransition parallelTransition;
    /**
     * Handler volajici dalsi krok.
     * Uplatneno nastaveni animace x krokovani.
     */
    private final EventHandler<ActionEvent> nextStepHandler;
    /**
     * Handler volajici krok.
     * Dalsi udalost vybrana, i kdyz je zapnuto krokovani.
     */
    private final EventHandler<ActionEvent> stepHandler;
    /**
     * Rychlost animace.
     * Udavana v nasobcich od normalni doby.<br/>
     * 1 - rychlost nezmenena<br/>
     * speed vetsi 1 - rychlejsi<br/>
     * speed mensi 1 - pomalejsi<br/>
     * speed = 0 - zastaveno
     */
    private double speed = 1;
    /**
     * Aktualni doba trvani jedne animace.
     */
    private Duration duration = NORMAL_DURATION;

    protected Graphics(Pane pane, ScrollPane scrollPane, T control) {
        this.control = control;
        this.pane = pane;
        this.scrollPane = scrollPane;

        this.nextStepHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Graphics.this.control.nextStep(); //TODO
            }
        };

        this.stepHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Graphics.this.control.step(); TODo
            }
        };
    }

    /**
     * Nastaveni rychlosti animace.
     * Pokud je rychlost=5, nastavi minimalni trvani => maximalni rychlost
     * @param speed rychlost v rozmezi 0-5
     * @throws IllegalArgumentException pokud je rychlost mimo pripustne meze
     */
    public final void setSpeed(double speed) {
        if (speed < 0 || speed > 5) {
            throw new IllegalArgumentException("rychlost musi byt v rozmezi 0-5");
        }
        this.speed = speed;
        if (parallelTransition != null) this.parallelTransition.setRate(speed);
        if (speed == 5) this.duration = MIN_DURATION;
        else this.duration = NORMAL_DURATION;
    }

    /**
     * Nastavi dobu trvani jedne animace na minimalni.
     */
    public final void setMinDuration() {
        this.duration = MIN_DURATION;
    }

    /**
     * Obnovi nastaveni trvani jedne animace podle nastaveni rychlosti.
     */
    public final void resetDuration() {
        if (this.speed == 5) this.duration = MIN_DURATION;
        else this.duration = NORMAL_DURATION;
    }

    /**
     * Vrati dobu trvani.
     * Pokud je nastaveno {@link #MIN_DURATION}, tak vrati {@link #MIN_DURATION},
     * jinak dle poctu zadanych milisekund.
     * @param millis pocet milisekund
     * @return
     */
    public final Duration getDuration(int millis) {
        if (duration == MIN_DURATION)
            return MIN_DURATION;
        else
            return Duration.millis(millis);
    }

    /**
     * Vypocte cas dle zadane vzdalenosti a rychlosti.
     * @param distance vzdalenost v pixelech
     * @param speed rychlost v pixelech za sekundu
     * @return
     */
    public final Duration getDurationByDistance(double distance, double speed) {
        if (duration == MIN_DURATION) {
            return MIN_DURATION;
        } else {
            if (speed <= 0) {
                throw new IllegalArgumentException("Speed must be positive.");
            }
            double time = Math.abs(distance) / speed;
            return Duration.millis(time * 1000);
        }
    }



    /**
     * Vytvori novou globalni animaci.
     * Tuto animaci lze ovladat z GUI.
     * @param animations animaci k pridani
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    protected final void transitionCreate(Animation... animations) {
        if (isAnimationRunning()) {
            throw new IllegalStateException("Another animation is running.");
        }
//        parallelTransition = RatedParallelTransition.build(speed); TODO
        if (animations != null) parallelTransition.getChildren().addAll(animations);
        parallelTransition.setOnFinished(nextStepHandler);
    }

    /**
     * Test zda probiha globalni animace.
     * @return true pokud animace bezi
     */
    public boolean isAnimationRunning() {
        if (parallelTransition != null && parallelTransition.getStatus() == Animation.Status.RUNNING) {
            return true;
        }
        return false;
    }

    /**
     * Nastavi udalost, ktera se vykona po dobehnutu globalni animace.
     * @param handler
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    protected final void transitionSetOnFinished(EventHandler<ActionEvent> handler) {
        checkTransition();
        parallelTransition.setOnFinished(handler);
    }

    /**
     * Po dobehnuti globalni animace je vykonan dalsi krok.
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    protected final void transitionSetNextStepOnFinished() {
        checkTransition();
        parallelTransition.setOnFinished(nextStepHandler);
    }

    /**
     * Prida animaci do globalni animace.
     * @param animation animace k pridani
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    protected final void transitionAdd(Animation animation) {
        checkTransition();
        parallelTransition.getChildren().add(animation);
    }

    /**
     * Prida vsechny animace do globalni animace.
     * @param animations animace k pridani
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    protected final void transitionAddAll(Animation... animations) {
        checkTransition();
        parallelTransition.getChildren().addAll(animations);
    }

    /**
     * Spusti globalni animaci.
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    protected final void transitionPlay() {
        checkTransition();
        parallelTransition.play();
    }

    /**
     * Vytvori novou globalni animaci z predane animace.
     * @param pt
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    protected final void transitionSet(ParallelTransition pt) {
        checkTransition();
        this.parallelTransition = pt;
        this.parallelTransition.setRate(speed);
        this.parallelTransition.setOnFinished(nextStepHandler);
    }

    /**
     * Zastavi globalni animaci.
     */
    protected final void transitionStop() {
        if (parallelTransition != null) {
            parallelTransition.stop();
            parallelTransition.getChildren().clear();
            parallelTransition = null;
        }
    }

    /**
     * Testuje globalni animaci.
     * @throws NullPointerException pokud je globalni animace null
     * @throws IllegalStateException pokud globalni animace prave probiha
     */
    private void checkTransition() {
        if (parallelTransition == null) {
            throw new NullPointerException("Animation is not initialized.");
        }
        if (parallelTransition.getStatus() == Animation.Status.RUNNING) {
            throw new IllegalStateException("Another animation is running.");
        }
    }

    /**
     * Spusti globalni animaci.
     */
    public void play() {
        if (parallelTransition != null) {
            parallelTransition.play();
        }
    }

    /**
     * Pozastavi globalni animaci.
     */
    public void pause() {
        if (parallelTransition != null) {
            parallelTransition.pause();
        }
    }

    //TODO
//    /**
//     * @see Controller#nextStep()
//     */
//    protected final void controlNextStep() {
//        control.nextStep();
//    }
//
//    /**
//     * @see Controller#step()
//     */
//    protected final void controlStep() {
//        control.step();
//    }
//
//    /**
//     * @see Controller#isAutoStep()
//     */
//    protected final boolean controlIsAutoNextStep() {
//        return control.isAutoStep();
//    }

    /**
     * Vrati ridici tridu.
     * @return
     */
    protected final T getControl() {
        return control;
    }



    /**
     * Vrati panel animace.
     * @return
     */
    public final Pane getPane() {
        return pane;
    }

    /**
     * Prida objekty do animacniho panelu.
     * @param nodes
     */
    public final void addComponent(Node... nodes) {
        pane.getChildren().addAll(nodes);
    }

    /**
     * Vymaze objekty z animacniho panelu.
     */
    protected final void clearComponents() {
        pane.getChildren().clear();
    }

    /**
     * Odstrani objekt z animacniho panelu.
     * @param node
     * @return
     */
    public final boolean removeComponent(Node node) {
        return pane.getChildren().remove(node);
    }



    /**
     * Vrati posunovatelny panel.
     * @see #scrollPane
     * @return
     */
    protected final ScrollPane getScrollPane() {
        return scrollPane;
    }



    /**
     * Vrati rychlost animace.
     * @see #speed
     * @return
     */
    protected final double getSpeed() {
        return speed;
    }

    /**
     * Vrati handler volajici dalsi krok.
     * @see #stepHandler
     * @return
     */
    protected EventHandler<ActionEvent> getStepHandler() {
        return stepHandler;
    }

}
