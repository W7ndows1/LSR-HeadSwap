package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;

public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack held = player.getItemInHand(hand);

            if (!held.is(Items.PLAYER_HEAD)) return InteractionResult.PASS;
            if (world.isClientSide()) return InteractionResult.CONSUME;

            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack head = held.copy();
            head.setCount(1);

            player.setItemSlot(EquipmentSlot.HEAD, head);

            if (helmet.isEmpty()) {
                held.shrink(1);
            } else {
                player.setItemInHand(hand, helmet);
            }

            return InteractionResult.SUCCESS;
        });
    }
}
