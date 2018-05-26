package world.sake.app.web.generate;

import org.lastaflute.web.Execute;
import org.lastaflute.web.login.AllowAnyoneAccess;
import org.lastaflute.web.response.JsonResponse;

import world.sake.app.web.base.FactoryBaseAction;

@AllowAnyoneAccess
public class GenerateAction extends FactoryBaseAction {

    @Execute
    public JsonResponse<GenerateContentResult> get$index(GenerateBody body) {
        GenerateContentResult result = new GenerateContentResult();
        result.success_flg = true;

        return asJson(result);
    }
}
