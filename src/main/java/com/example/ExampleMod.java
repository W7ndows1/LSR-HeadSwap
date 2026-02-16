package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class ExampleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        UseItemCallback.EVENT.register((player, world, hand) -> {

            // Only run client side
            if (!world.isClient()) {
                return ActionResult.PASS;
            }

            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayer = client.player;

            if (clientPlayer == null || client.interactionManager == null) {
                return ActionResult.PASS;
            }

            // Only trigger if holding a player head
            ItemStack held = clientPlayer.getStackInHand(hand);
            if (!held.isOf(Items.PLAYER_HEAD)) {
                return ActionResult.PASS;
            }

            int selectedHotbar = clientPlayer.getInventory().selectedSlot;
            int helmetSlot = 39; // helmet slot index in player inventory
            int syncId = clientPlayer.currentScreenHandler.syncId;

            // Swap hotbar slot with helmet slot (vanilla-style swap)
            client.interactionManager.clickSlot(
                    syncId,
                    helmetSlot,
                    selectedHotbar,
                    SlotActionType.SWAP,
                    clientPlayer
            );

            return ActionResult.SUCCESS;
        });
    }
}
