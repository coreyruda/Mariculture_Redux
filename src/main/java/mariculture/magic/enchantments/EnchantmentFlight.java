package mariculture.magic.enchantments;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.client.FMLClientHandler;

public class EnchantmentFlight extends EnchantmentJewelry {
	public EnchantmentFlight(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("flight");
	}
	
	@Override
	public int getMinEnchantability(int level) {
		return 58;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return false;
	}

	public static int mode = 0;
	public static int maxMode = 0;
	private static int damageTicker = 0;
	
	public static void damage(EntityPlayer player) {
		if (player.getEntityData().hasKey("SupermanIsFlying")) {
			if(player.capabilities.isFlying && !player.capabilities.isCreativeMode) {
				damageTicker++;

				if (damageTicker >= 300) {
					damageTicker = 0;

					EnchantHelper.damageItems(Magic.flight, player, 1);
				}
			}
		}
	}

	public static void set(int flight, EntityPlayer player) {
		maxMode = flight - 1;
		
		if (!player.capabilities.isCreativeMode) {
			if (maxMode > 0) {				
				float flightSpeed = (mode + 1) * 0.025F;
				player.getEntityData().setBoolean("SupermanIsFlying", true);
				player.capabilities.allowFlying = true;
				player.capabilities.setFlySpeed(flightSpeed);
				player.fallDistance = 0F;
			} else {
				// Deactivate
				if (player.getEntityData().hasKey("SupermanIsFlying")) {
					player.getEntityData().removeTag("SupermanIsFlying");
					player.capabilities.allowFlying = false;
					player.capabilities.isFlying = false;
					player.capabilities.setFlySpeed(0.05F);
					mode = 0;
				}
			}
		}
	}
}
