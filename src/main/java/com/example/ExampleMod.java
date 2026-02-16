package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.world.entity.EquipmentSlot;
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

            // Only run on server
            if (world.isClientSide()) {
                return InteractionResult.PASS;
            }

            if (!held.is(Items.PLAYER_HEAD)) {
                return InteractionResult.PASS;
            }

            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

            // Copy 1 head to equip
            ItemStack toEquip = held.copy();
            toEquip.setCount(1);

            // Equip it
            player.setItemSlot(EquipmentSlot.HEAD, toEquip);

            // Swap logic
            if (helmet.isEmpty()) {
                held.shrink(1);
            } else {
                player.setItemInHand(hand, helmet);
            }

            return InteractionResult.SUCCESS;
        });
    }
}
