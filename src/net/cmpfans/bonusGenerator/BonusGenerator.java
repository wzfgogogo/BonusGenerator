package net.cmpfans.bonusGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BonusGenerator {

	// 递归深度步进
	private static final int MAXDEPTH = 3000;

	private Random random = new Random();

	// 最终结果list
	private List<Long> bonusList = null;
	// 红包金额总数
	private long sum;
	// 红包数量
	private long sliceAmount;
	// 当前计算到的红包数
	private long currentSliceAmount;
	// 当前红包金额量
	private long currentSum;

	// 递归深度计数
	private int depth;
	// 临时单个红包变量
	private long tempBonus;

	// 结果list总和
	private long outSum;
	
	public BonusGenerator() {
		this.sum = 100l;
		this.sliceAmount = 20l;
	}

	public BonusGenerator(long sum, long sliceAmount) {
		this.sum = sum;
		this.sliceAmount = sliceAmount;

	}

	/**
	 * <p>Description: 生成随机红包list</p>
	 * @return List<Long>
	 */
	public List<Long> distributeRandomBonus() {
		if (this.sliceAmount <= this.sum && this.sum != 0) {
			this.bonusList = new ArrayList<Long>();
			this.currentSliceAmount = this.sliceAmount;
			this.currentSum = this.sum;
			while (this.currentSliceAmount != 1) {
				initOneBonus(this.bonusList);
				depth = 0;
			}
			return this.bonusList;
		} else {
			return null;
		}
	}

	private void initOneBonus(List<Long> bonusList) {
		depth++;
		if (depth % MAXDEPTH == 0) {
			return;
		}
		if (currentSliceAmount != 1) {
			tempBonus = nextLong(random, (currentSum / currentSliceAmount) * 2 - 1) + 1;
			currentSum = currentSum - tempBonus;
			currentSliceAmount = currentSliceAmount - 1;
			bonusList.add(tempBonus);
			initOneBonus(bonusList);
		} else {
			bonusList.add(currentSum);
			return;
		}
	}

	private long nextLong(Random rng, long n) {
		// error checking and 2^x checking removed for simplicity.
		long bits, val;
		do {
			bits = (rng.nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0L);
		return val;
	}

	public static void main(String[] args) {
		BonusGenerator a = new BonusGenerator(100000000l, 6000l);
		long timeA = System.currentTimeMillis();
		a.distributeRandomBonus();

		System.out.println("生成随机红包耗时:" + String.valueOf(System.currentTimeMillis() - timeA + "ms"));

		for (Long num : a.bonusList) {
			a.outSum = a.outSum + num;
		}
		System.out.println("金额总和校验:" + a.outSum);

	}

}