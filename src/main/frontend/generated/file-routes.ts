import type { AgnosticRoute } from "@vaadin/hilla-file-router/types.js";
import { createRoute } from "@vaadin/hilla-file-router/runtime.js";
import * as Page0 from "../views/@index.js";
import * as Page1 from "../views/chat.js";
import * as Layout2 from "../views/@layout.js";
const routes: readonly AgnosticRoute[] = [
    createRoute("", false, Layout2, [
        createRoute("", false, Page0),
        createRoute("chat", false, Page1)
    ])
];
export default routes;
