package rubedo.items.tools.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class ToolRepairRecipes implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}
	/*private ToolProperties tool;
	private ItemStack modifier;

	@Override
	public boolean matches(InventoryCrafting inventorycrafting, World world) {
		this.tool = null;
		this.modifier = null;

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				ItemStack itemstack = inventorycrafting.getStackInRowAndColumn(
						j, i);

				if (itemstack != null) {
					if (itemstack.getItem() instanceof ToolBase) {
						if (this.tool != null)
							return false;

						this.tool = ((ToolBase) itemstack.getItem())
								.getToolProperties(itemstack);
					} else {
						if (this.modifier != null)
							return false;

						this.modifier = itemstack;
					}
				}
			}

		if (this.tool == null || this.modifier == null)
			return false;

		// TODO: optimize this, it's slow, though the two step
		// process already
		// ensures this doesn't run until you put a ToolBase in the
		// crafting grid
		for (ItemStack material : ContentTools.materialStacks.keySet()) {
			// TODO: This is just so WTF I don't even words, but
			// apparently there's a null key (something to do with ingots
			// apparently)
			if (material == null)
				continue;

			if (material.getItem().equals(this.modifier.getItem())
					&& material.getItemDamage() == this.modifier
					.getItemDamage()) {
				this.modifier = material;

				Material toolMaterial = ContentTools.materialStacks.get(material);

				// TODO: find alternate way to compare tool heads
				if (toolMaterial.rodMaterial == material
						|| toolMaterial.capMaterial == material) {
					return true;
				} else if (OreDictionary.itemMatches(toolMaterial.getToolHead(this.tool.getItem().getName()), material, true)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventorycrafting) {
		Material material = ContentTools.materialStacks.get(this.modifier);

		ToolProperties copy = this.tool.getItem()
				.getToolProperties(this.tool.getStack().copy());

		if (ContentTools.toolCaps.containsKey(material.name)
				&& ContentTools.toolCaps.get(material.name).capMaterial == this.modifier) {
			copy.setCapMaterial(material.name);
			return copy.getStack();
		}

		if (ContentTools.toolRods.containsKey(material.name)
				&& ContentTools.toolRods.get(material.name).rodMaterial == this.modifier) {
			copy.setRodMaterial(material.name);
			return copy.getStack();
		}

		// Bit weird, but at this point we're sure it's a known tool head anyway

		copy.setBroken(false);
		copy.setHeadMaterial(material.name);
		copy.getStack().setItemDamage(0);
		return copy.getStack();
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}*/
}