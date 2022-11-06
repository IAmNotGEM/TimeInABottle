package lumien.randomthings;

import lumien.randomthings.config.ModConfiguration;
import lumien.randomthings.entitys.ModEntitys;
import lumien.randomthings.handler.RTEventHandler;
import lumien.randomthings.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "tiab", name = "TimeInABottle", version = "1.0.1")
public class TimeInABottle
{
    @Instance("tiab")
    public static TimeInABottle instance;
    @SidedProxy(clientSide = "lumien.randomthings.client.ClientProxy", serverSide = "lumien.randomthings.CommonProxy")
    public static CommonProxy proxy;
    public Logger logger;
    public ModConfiguration configuration;
    ASMDataTable asmDataTable;
    public ASMDataTable getASMData() {
        return this.asmDataTable;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        this.asmDataTable = event.getAsmData();
        this.logger = event.getModLog();
        this.configuration = new ModConfiguration();
        this.configuration.preInit(event);
        ModItems.load(event);
        ModEntitys.init();
        proxy.registerModels();
        RTEventHandler eventHandler = new RTEventHandler();
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event) {
        proxy.registerRenderers();
    }
}
