import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import Home from "@/views/Home.vue";
import Imformation from "@/views/Imformation.vue";
import UserManagement from "@/views/UserManagement.vue"; // 修改路径
import Authoritymanagement from "@/views/Authoritymanagement.vue";
import Organizationmanagement from "@/views/Organizationmanagement.vue";
import Organizationmember from "@/views/Organizationmember.vue";
import Homepage from "@/views/Homepage.vue";
import NotificationManagement from "@/views/NotificationManagement.vue";
import Organization from "@/views/Organization.vue";
// 1: 创建路由组件
Vue.use(VueRouter);

// 2: 将路由与组件进行映射
const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    component: Login,
    name: "login"
  },
  {
    path: "/register",
    component: Register,
    name: "register"
  },
  {
    path: "/home",
    component: Home,
    name: "home",
    children: [
      { path: "imformation", component: Imformation, name: "imformation" },
      { path: "usermanagement", component: UserManagement, name: "userManagement" },
      {path:"Authoritymanagement",component:Authoritymanagement,name: "Authoritymanagement"},
      {path:"Organizationmanagement",component:Organizationmanagement,name: "Organizationmanagement"},
      {path:"Organizationmember",component:Organizationmember,name: "Organizationmember"},
      {path:"Homepage",component:Homepage,name: "Homepage"},
      {path:"NotificationManagement",component:NotificationManagement,name: "NotificationManagement"},
      {path:"Organization",component:Organization,name: "Organization"}






    ]
  }
];

// 3: 创建 router 实例
const router = new VueRouter({
  routes
});

export default router;
