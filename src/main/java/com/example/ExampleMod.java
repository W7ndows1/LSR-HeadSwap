package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {

        UseItemCallback.EVENT.register((player, world, hand) -> {

            // Only run on server
            if (world.isClient()) {
                return ActionResult.PASS;
            }

            ItemStack held = player.getStackInHand(hand);

            // Only act if holding a player head
            if (!held.isOf(Items.PLAYER_HEAD)) {
                return ActionResult.PASS;
            }

            ItemStack currentHelmet = player.getEquippedStack(EquipmentSlot.HEAD);

            // Copy one head to equip
            ItemStack headToEquip = held.copy();
            headToEquip.setCount(1);

            // Equip it
            player.equipStack(EquipmentSlot.HEAD, headToEquip);

            // Handle swap logic like normal helmets
            if (currentHelmet.isEmpty()) {
                held.decrement(1);
            } else {
                player.setStackInHand(hand, currentHelmet);
            }

            return ActionResult.SUCCESS;
        });
    }
}
