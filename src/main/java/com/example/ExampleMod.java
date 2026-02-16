package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {

        UseItemCallback.EVENT.register((player, world, hand) -> {

            ItemStack held = player.getStackInHand(hand);

            // Run only on server
            if (world.isClient()) {
                return TypedActionResult.pass(held);
            }

            if (!held.isOf(Items.PLAYER_HEAD)) {
                return TypedActionResult.pass(held);
            }

            ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

            // Equip one head
            ItemStack toEquip = held.copy();
            toEquip.setCount(1);
            player.equipStack(EquipmentSlot.HEAD, toEquip);

            if (helmet.isEmpty()) {
                held.decrement(1);
            } else {
                player.setStackInHand(hand, helmet);
            }

            return TypedActionResult.success(held);
        });
    }
}
