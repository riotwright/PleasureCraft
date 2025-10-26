package com.sandymandy.pleasurecraft.util.variables;

import com.sandymandy.pleasurecraft.networking.codec.PacketCodecExtra;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.ArrayList;
import java.util.List;

public class SceneOptions{

    private final String displayName;
    private final int requiredRelationshipLevel;
    private final List<String> introAnim;
    private final List<String> slowAnim;
    private final List<String> fastAnim;
    private final String cumAnim;
    private final float cumThreshold;
    private final boolean needsToStrip;
    private final boolean isBedScene;
    private final boolean useKeyFrameEvents;
    private final float bedAlignmentOffset;
    private final List<String> bedIdle;

    public static final SceneOptions EMPTY = new SceneOptions("", 0,new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),"", 0f,false, false, false, 0f, new ArrayList<>());

    public static final PacketCodec<RegistryByteBuf, SceneOptions> PACKET_CODEC = PacketCodecExtra.tuple(
            PacketCodecs.STRING, SceneOptions::displayName,
            PacketCodecs.VAR_INT, SceneOptions::requiredRelationshipLevel,
            PacketCodecs.collection(ArrayList::new , PacketCodecs.STRING), SceneOptions::introAnim,
            PacketCodecs.collection(ArrayList::new , PacketCodecs.STRING), SceneOptions::slowAnim,
            PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING), SceneOptions::fastAnim,
            PacketCodecs.STRING, SceneOptions::cumAnim,
            PacketCodecs.FLOAT, SceneOptions::cumThreshold,
            PacketCodecs.BOOLEAN, SceneOptions::needsToStrip,
            PacketCodecs.BOOLEAN, SceneOptions::isBedScene,
            PacketCodecs.BOOLEAN, SceneOptions::useKeyFrameEvents,
            PacketCodecs.FLOAT, SceneOptions::bedAlignmentOffset,
            PacketCodecs.collection(ArrayList::new, PacketCodecs.STRING), SceneOptions::bedIdle,
            SceneOptions::new
    );

    private SceneOptions(String displayName, int requiredRelationshipLevel, List<String> introAnim, List<String> slowAnim, List<String> fastAnim, String cumAnim, float cumThreshold, boolean needsToStrip, boolean isBedScene, boolean useKeyFrameEvents, float bedAlignmentOffset, List<String> bedIdle){
        this.displayName = displayName;
        this.requiredRelationshipLevel = requiredRelationshipLevel;
        this.introAnim = introAnim;
        this.slowAnim = slowAnim;
        this.fastAnim = fastAnim;
        this.cumAnim = cumAnim;
        this.cumThreshold = cumThreshold;
        this.needsToStrip = needsToStrip;
        this.isBedScene = isBedScene;
        this.useKeyFrameEvents = useKeyFrameEvents;
        this.bedAlignmentOffset = bedAlignmentOffset;
        this.bedIdle = bedIdle;
    }

    public final String displayName() {return this.displayName;}
    public final int requiredRelationshipLevel() {return this.requiredRelationshipLevel;}
    public final List<String> introAnim() {return this.introAnim;}
    public final List<String> slowAnim() {return this.slowAnim;}
    public final List<String> fastAnim() {return this.fastAnim;}
    public final String cumAnim() {return this.cumAnim;}
    public final float cumThreshold() {return this.cumThreshold;}
    public final boolean needsToStrip() {return this.needsToStrip;}
    public final boolean isBedScene() {return this.isBedScene;}
    public final boolean useKeyFrameEvents() {return this.useKeyFrameEvents;}
    public final float bedAlignmentOffset() {return this.bedAlignmentOffset;}
    public final List<String> bedIdle() {return this.bedIdle;}

    public static SceneOptions create(
            String name,
            int requiredRelationshipLevel,
            List<String> introAnim,
            List<String> slowAnim,
            List<String> fastAnim,
            String cumAnim,
            float cumThreshold,
            boolean needsToStrip,
            boolean useKeyFrameEvents,
            float bedOffset,
            List<String> bedIdle
    ){
        return new SceneOptions(name, requiredRelationshipLevel, introAnim, slowAnim, fastAnim, cumAnim, cumThreshold, needsToStrip, true, useKeyFrameEvents,bedOffset, bedIdle);
    }

    public static SceneOptions create(
            String name,
            int requiredRelationshipLevel,
            List<String> introAnim,
            List<String> slowAnim,
            List<String> fastAnim,
            String cumAnim,
            float cumThreshold,
            boolean needsToStrip
    ){
        return new SceneOptions(name, requiredRelationshipLevel, introAnim, slowAnim, fastAnim, cumAnim, cumThreshold, needsToStrip, false, false, 0f, new ArrayList<>());
    }


}