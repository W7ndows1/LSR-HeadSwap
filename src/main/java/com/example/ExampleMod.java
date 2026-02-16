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

        // ✅ Only run logic on server
        if (world.isClient()) {
            return InteractionResult.PASS;
        }

        ItemStack held = player.getStackInHand(hand);

        if (!held.isOf(Items.PLAYER_HEAD)) {
            return InteractionResult.PASS;
        }

        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack head = held.copy();
        head.setCount(1);

        player.equipStack(EquipmentSlot.HEAD, head);

        if (helmet.isEmpty()) {
            held.decrement(1);
        } else {
            player.setStackInHand(hand, helmet);
        }

        return InteractionResult.SUCCESS;
    });
}
