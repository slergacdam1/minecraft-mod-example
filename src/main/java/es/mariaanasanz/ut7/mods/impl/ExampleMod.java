package es.mariaanasanz.ut7.mods.impl;

import es.mariaanasanz.ut7.mods.base.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod(DamMod.MOD_ID)
public class ExampleMod extends DamMod implements IBlockBreakEvent, IServerStartEvent,
        IItemPickupEvent, ILivingDamageEvent, IUseItemEvent, IFishedEvent,
        IInteractEvent, IMovementEvent {


    public ExampleMod(){
        super();

    }

    @Override
    public String autor() {
        return "Santi Lerga ";
    }

    @Override
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        System.out.println("Bloque destruido en la posicion "+pos);
    }

    @Override
    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        LOGGER.info("Server starting");
    }

    @Override
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        ItemStack itemStack  = event.getItem().getItem();
        System.out.println("Item recogido: " + itemStack);
    }

    @Override
    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        System.out.println("evento LivingDamageEvent invocado "+event.getEntity().getClass()+" provocado por "+event.getSource().getEntity());
    }

    @Override
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        System.out.println("evento LivingDeathEvent invocado "+event.getEntity().getClass()+" provocado por "+event.getSource().getEntity());

    }

    @Override
    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        LOGGER.info("evento LivingEntityUseItemEvent invocado "+event.getEntity().getClass());
    }


    @Override
    @SubscribeEvent
    public void onPlayerFish(ItemFishedEvent event) {
        Player jugador = event.getEntity();
        int nivel = jugador.experienceLevel;
        NonNullList<ItemStack> itemStack = event.getDrops();
        System.out.println(" ¡Has pescado un pez! " + itemStack.toString() + " y el nivel de experiencia es: " + nivel);
        int numeroRandom = (int) (Math.random() * 100 + 1);
        int probabilidad = nivel;
        if (numeroRandom >= probabilidad) {
            for (ItemStack stack : itemStack) {
                if (stack.toString().toLowerCase().contains("cod")) {
                    ItemStack cocinado = new ItemStack(Items.COOKED_COD, 1);
                    jugador.getInventory().add(cocinado);
                    System.out.println(" acabas de conseguir un item adicional: " + cocinado.toString());
                } else if (stack.toString().toLowerCase().contains("salmon")) {
                    ItemStack cocinado = new ItemStack(Items.COOKED_SALMON, 1);
                    jugador.getInventory().add(cocinado);
                    System.out.println(" acabas de conseguir un item adicional: " + cocinado.toString());
                } else if (stack.toString().toLowerCase().contains("pufferfish")) {
                    System.out.println("este pescado no se puede cocinar");
                } else if (stack.toString().toLowerCase().contains("tropical")) {
                    System.out.println("este pescado no se puede cocinar");
                } else {
                    ItemStack objeto = objetoAleatorio();
                    jugador.getInventory().add(objeto);
                    System.out.println("acabas de conseguir un objeto aleatorio " + objeto.toString());
                }

            }
        }
    }

    private ItemStack objetoAleatorio(){
        Item[] itemsAleatorios = {Items.BOOK, Items.BOW, Items.CLOCK, Items.DIAMOND};
        int numeroAleatorio = (int) (Math.random() * itemsAleatorios.length);
        return new ItemStack(itemsAleatorios[numeroAleatorio], 1);

    }

    @Override
    @SubscribeEvent
    public void onPlayerTouch(PlayerInteractEvent.RightClickBlock event) {
        System.out.println("¡Has hecho click derecho!");
        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();
        if (ItemStack.EMPTY.equals(heldItem)) {
            System.out.println("La mano esta vacia");
            if (state.getBlock().getName().getString().trim().toLowerCase().endsWith("log")) {
                System.out.println("¡Has hecho click sobre un tronco!");
            }
        }
    }


    @Override
    @SubscribeEvent
    public void onPlayerWalk(MovementInputUpdateEvent event) {
        if(event.getEntity() instanceof Player){
            if(event.getInput().down){
                System.out.println("down"+event.getInput().down);
            }
            if(event.getInput().up){
                System.out.println("up"+event.getInput().up);
            }
            if(event.getInput().right){
                System.out.println("right"+event.getInput().right);
            }
            if(event.getInput().left){
                System.out.println("left"+event.getInput().left);
            }
        }
    }
}
