package org.boilit.ebm.engine;

import java.util.Map;
import java.util.Properties;
import webit.script.CFG;

/**
 * @author zqq
 * @see
 */
public final class WebitScript_NoASMNative extends WebitScript {

    @Override
    protected void initConfig(Map<String, Object> parameters, Properties properties) {
        super.initConfig(parameters, properties);
        parameters.put(CFG.ASM_NATIVE, "false");
    }
}
