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

public class HeadSwapClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        UseItemCallback.EVENT.register((player, world, hand) -> {

            if (!world.isClient) {
                return ActionResult.PASS;
            }

            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayer = client.player;

            if (clientPlayer == null || client.interactionManager == null) {
                return ActionResult.PASS;
            }

            ItemStack held = clientPlayer.getStackInHand(hand);

            if (!held.isOf(Items.PLAYER_HEAD)) {
                return ActionResult.PASS;
            }

            int selectedHotbar = clientPlayer.getInventory().selectedSlot;
            int helmetSlot = 39;
            int syncId = clientPlayer.currentScreenHandler.syncId;

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
