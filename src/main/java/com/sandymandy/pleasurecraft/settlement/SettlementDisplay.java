package com.sandymandy.pleasurecraft.settlement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;

/**
 * Defines how a Settlement Tab or Node is visually displayed in the GUI.
 * Similar to AdvancementDisplay, but simpler and thematically specific.
 */
public class SettlementDisplay {
    public static final Codec<SettlementDisplay> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.VALIDATED_CODEC.fieldOf("icon").forGetter(SettlementDisplay::getIcon),
                    TextCodecs.CODEC.fieldOf("title").forGetter(SettlementDisplay::getTitle),
                    TextCodecs.CODEC.fieldOf("description").forGetter(SettlementDisplay::getDescription),
                    Identifier.CODEC.fieldOf("background").forGetter(SettlementDisplay::getBackground)
            ).apply(instance, SettlementDisplay::new
            )
    );

    public static final PacketCodec<RegistryByteBuf, SettlementDisplay> PACKET_CODEC =
            PacketCodec.of(SettlementDisplay::toPacket, SettlementDisplay::fromPacket);

    private final ItemStack icon;
    private final Text title;
    private final Text description;
    private final Identifier background;
    private float x;
    private float y;

    public SettlementDisplay(ItemStack icon, Text title, Text description, Identifier background) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.background = background;
    }

    // --- Accessors ---
    public ItemStack getIcon() {
        return icon;
    }

    public Text getTitle() {
        return title;
    }

    public Text getDescription() {
        return description;
    }

    public Identifier getBackground() {
        return background;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // --- Serialization ---
    private void toPacket(RegistryByteBuf buf) {
        ItemStack.PACKET_CODEC.encode(buf, icon);
        TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC.encode(buf, title);
        TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC.encode(buf, description);

        buf.writeFloat(x);
        buf.writeFloat(y);
    }

    public static SettlementDisplay ofBasic(Text title, Text description) {
        return new SettlementDisplay(
                net.minecraft.item.Items.BOOK.getDefaultStack(),
                title,
                description,
                Identifier.ofVanilla("textures/gui/advancements/backgrounds/stone.png")

        );
    }

    public static SettlementDisplay create(ItemStack icon, Text title, Text description, Identifier background){
        return new SettlementDisplay(
                icon,
                title,
                description,
                background
        );
    }

    private static SettlementDisplay fromPacket(RegistryByteBuf buf) {
        ItemStack icon = ItemStack.PACKET_CODEC.decode(buf);
        Text title = TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC.decode(buf);
        Text desc = TextCodecs.UNLIMITED_REGISTRY_PACKET_CODEC.decode(buf);
        Identifier background = buf.readIdentifier();
        return new SettlementDisplay(icon, title, desc, background);
    }
}
