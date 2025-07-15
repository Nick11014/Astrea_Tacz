package com.tacz.guns.util;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;

public final class CycleTaskHelper {
    private static final List<CycleTaskHelper.CycleTaskTicker> CYCLE_TASKS = new LinkedList<>();
    private static final List<CycleTaskHelper.CycleTaskTicker> TEMP_CYCLE_TASKS = new LinkedList<>();

    /**
     * ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¤Ã‚Â¾Ã¢â‚¬ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ§Ã‚Â«Ã¢â‚¬Â¹ÃƒÂ¥Ã‹â€ Ã‚Â»ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param task     ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â»Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ boolean ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã¢â‚¬Â Ã‚Â³ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ§Ã‚Â»Ã‚Â§ÃƒÂ§Ã‚Â»Ã‚Â­ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ false ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â Ã‚ÂÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @param periodMs ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @param cycles   ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡-1 ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public static void addCycleTask(BooleanSupplier task, long periodMs, int cycles) {
        CycleTaskHelper.CycleTaskTicker ticker = new CycleTaskHelper.CycleTaskTicker(task, periodMs, cycles);
        if (ticker.tick()) {
            CYCLE_TASKS.add(ticker);
        }
    }

    public static void addCycleTask(BooleanSupplier task, long delayMs, long periodMs, int cycles) {
        if (delayMs <= 0) {
            addCycleTask(task, periodMs, cycles);
            return;
        }
        CycleTaskHelper.CycleTaskTicker ticker = new CycleTaskHelper.CycleTaskTicker(task, delayMs, periodMs, cycles);
        CYCLE_TASKS.add(ticker);
    }

    public static void tick() {
        TEMP_CYCLE_TASKS.addAll(CYCLE_TASKS);
        CYCLE_TASKS.clear();
        TEMP_CYCLE_TASKS.removeIf(ticker -> !ticker.tick());
        CYCLE_TASKS.addAll(TEMP_CYCLE_TASKS);
        TEMP_CYCLE_TASKS.clear();
    }

    private static class CycleTaskTicker {
        private final BooleanSupplier task;
        private final float periodS;
        private final int cycles;
        private float delayS = 0;
        private long timestamp = -1;
        private float compensation = 0;
        private int count = 0;

        private CycleTaskTicker(BooleanSupplier task, long periodMs, int cycles) {
            this.task = task;
            this.periodS = periodMs / 1000f;
            this.cycles = cycles;
        }

        private CycleTaskTicker(BooleanSupplier task, long delayMs, long periodMs, int cycles) {
            this.delayS = delayMs / 1000f;
            this.timestamp = System.currentTimeMillis();
            this.task = task;
            this.periodS = periodMs / 1000f;
            this.cycles = cycles;
        }

        private boolean tick() {
            if (timestamp == -1) {
                timestamp = System.currentTimeMillis();
                if (cycles > 0 && ++count > cycles) {
                    return false;
                }
                return task.getAsBoolean();
            }
            float duration = (System.currentTimeMillis() - timestamp) / 1000f + compensation;
            if (delayS > 0) {
                if (delayS > duration) {
                    delayS = delayS - duration;
                    return true;
                } else {
                    delayS = 0;
                    duration = duration - delayS + periodS;
                }
            }
            if (duration > periodS) {
                compensation = duration;
                timestamp = System.currentTimeMillis();
                while (compensation > periodS) {
                    if (cycles > 0 && ++count > cycles) {
                        return false;
                    }
                    if (!task.getAsBoolean()) {
                        return false;
                    }
                    compensation -= periodS;
                }
            }
            return true;
        }
    }
}































































