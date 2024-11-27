
import ReactDOM from 'react-dom/client';
import {RouterProvider} from "react-router-dom";
import {router} from "./router";
import React from "react";
import {Provider} from 'react-redux'
import {store} from "./redux/store";
import "./index.css"

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
    <Provider store={store}>
<RouterProvider router={router}/>
    </Provider>
);


