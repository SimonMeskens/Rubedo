package rubedo.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import rubedo.common.ContentTools;
import rubedo.util.Singleton;
import cpw.mods.fml.common.registry.GameRegistry;

public class ToolAxe extends ToolBase {

	private ItemAxe vanillaEquivalent;

	public ToolAxe() {
		super();

		this.vanillaEquivalent = new ItemAxe(ToolMaterial.EMERALD) {
		};
		this.vanillaEquivalent.setUnlocalizedName("hatchetDiamond")
				.setTextureName("diamond_axe");
		GameRegistry.registerItem(this.vanillaEquivalent, "dummy_axe");

		this.allowedEnchants.add(EnumEnchantmentType.digger);
	}

	@Override
	public String getName() {
		return "axe";
	}

	@Override
	public float getWeaponDamage() {
		return 4.0F;
	}

	@Override
	public int getItemDamageOnHit() {
		return 2;
	}

	@Override
	public int getItemDamageOnBreak() {
		return 1;
	}

	@Override
	public boolean hitEntity(ItemStack stack,
			EntityLivingBase par2EntityLivingBase,
			EntityLivingBase par3EntityLivingBase) {
		ToolProperties properties = this.getToolProperties(stack);

		int ticks = properties.getSpecial() * 40;

		if (!properties.isBroken())
			par2EntityLivingBase.addPotionEffect(new PotionEffect(Potion.wither
					.getId(), ticks, 1, false));

		return super.hitEntity(stack, par2EntityLivingBase,
				par3EntityLivingBase);
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[] { Material.plants, Material.gourd, Material.wood };
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public ItemStack buildTool(rubedo.common.materials.MaterialMultiItem head,
			rubedo.common.materials.MaterialMultiItem rod,
			rubedo.common.materials.MaterialMultiItem cap) {
		ContentTools contentTools = Singleton.getInstance(ContentTools.class);
		ItemStack tool = new ItemStack(contentTools.getItem(ToolAxe.class));

		super.buildTool(tool, head, rod, cap);

		return tool;
	}

	@Override
	protected Item getEquivalentTool() {
		return this.vanillaEquivalent;
	}
}
