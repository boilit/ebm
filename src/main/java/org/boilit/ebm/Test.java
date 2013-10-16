package org.boilit.ebm;

/**
 * @author Boilit
 * @see
 */
public final class Test {
    public static void main(String[] args) throws Exception {
        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.JdkStringBuffer"});
        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.JdkStringBuilder"});
//        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.BSL"});
//        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.WebitScript"});
//        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.HTTL"});
//        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.BeeTL"});
//        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.Velocity"});
//        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.FreeMarker"});
//        Executor.main(new String[]{"-engine", "org.boilit.ebm.engine.Rythm"});
    }
}
