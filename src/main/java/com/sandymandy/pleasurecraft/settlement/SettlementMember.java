package com.sandymandy.pleasurecraft.settlement;

import org.jetbrains.annotations.Nullable;

public interface SettlementMember {
    @Nullable Settlement getSettlement();
    void setSettlement(@Nullable Settlement settlement);
    default boolean hasSettlement() { return getSettlement() != null; }
}
