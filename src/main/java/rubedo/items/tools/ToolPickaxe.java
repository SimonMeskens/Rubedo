package rubedo.items.tools;

import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import rubedo.common.ContentTools;

public class ToolPickaxe extends ToolBase {

	public ToolPickaxe(int id) {
		super(id);
	}

	@Override
	public String getName() {
		return "pickaxe";
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
	public float getEffectiveBlockSpeed() {
		return 4.0F;
	}

	@Override
	public Material[] getEffectiveMaterials() {
		return new Material[0];
	}

	@Override
	public Block[] getEffectiveBlocks() {
		return new Block[0];
	}

	@Override
	public ItemStack buildTool(String head, String rod, String cap) {
		ItemStack tool = new ItemStack(ContentTools.toolPickaxe);

		super.buildTool(tool, head, rod, cap);

		return tool;
	}

	@Override
	public List<Integer> getAllowedEnchantments() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
		// TODO: find a way to get metadata so this works properly
		return MinecraftForge.getBlockHarvestLevel(par1Block, 0, "pickaxe") <= this
				.getToolProperties(itemStack).getMiningLevel();
	}
}
