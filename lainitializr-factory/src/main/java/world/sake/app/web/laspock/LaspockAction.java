package world.sake.app.web.laspock;

import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.JsonResponse;

import world.sake.app.web.base.FactoryBaseAction;

/**
 * Laspock の動作を確認するためだけのAPI
 * @author neginuki
 */
@AllowAnyoneAccess
public class LaspockAction extends FactoryBaseAction {

    @Execute
    public JsonResponse<LaspockContentResult> get$index(LaspockBody body) {
        LaspockContentResult result = new LaspockContentResult();
        result.success_flg = true;

        return asJson(result);
    }
}
