package lt.jsj.qust.jjlin.helpers;

import com.squareup.otto.Bus;

/**
 * Created by Adam on 2015/1/3.
 */
public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
    }
}
