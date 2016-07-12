/**
 * Oshi (https://github.com/dblock/oshi)
 *
 * Copyright (c) 2010 - 2016 The Oshi Project Team
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Maintainers:
 * dblock[at]dblock[dot]org
 * widdis[at]gmail[dot]com
 * enrico.bianchi[at]gmail[dot]com
 *
 * Contributors:
 * https://github.com/dblock/oshi/graphs/contributors
 */
package oshi.hardware.platform.unix.freebsd;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import oshi.hardware.common.AbstractSensors;
import oshi.jna.platform.unix.LibC;

public class FreeBsdSensors extends AbstractSensors {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCpuTemperature() {
        // Try with kldload coretemp
        double sumTemp = 0d;
        int cpu = 0;
        String name = "dev.cpu.%d.temperature";
        while (true) {
            IntByReference size = new IntByReference(LibC.INT_SIZE);
            Pointer p = new Memory(size.getValue());
            if (0 != LibC.INSTANCE.sysctlbyname(String.format(name, cpu), p, size, null, 0)) {
                break;
            }
            sumTemp += p.getInt(0) / 10d - 273.15;
            cpu++;
        }
        if (cpu > 0) {
            return sumTemp / cpu;
        }
        // TODO try other ways here
        return 0d;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getFanSpeeds() {
        // TODO try common software
        int[] fans = new int[0];
        return fans;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCpuVoltage() {
        // TODO try common software
        double voltage = 0d;
        return voltage;
    }
}
