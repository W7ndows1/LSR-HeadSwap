package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack held = player.getStackInHand(hand);

            // Only player heads
            if (!held.isOf(Items.PLAYER_HEAD)) return ActionResult.PASS;

            // Server only
            if (world.isClient()) return ActionResult.SUCCESS;

            ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack head = held.copy();
            head.setCount(1);

            player.equipStack(EquipmentSlot.HEAD, head);

            if (helmet.isEmpty()) {
                held.decrement(1);
            } else {
                player.setStackInHand(hand, helmet);
            }

            return ActionResult.SUCCESS;
        });
    }
}
