import { RouterModule } from "@angular/router";
import { NgModule } from "@angular/core";
import { DashboardDemoComponent } from "./demo/view/dashboarddemo.component";
import { FormLayoutDemoComponent } from "./demo/view/formlayoutdemo.component";
import { PanelsDemoComponent } from "./demo/view/panelsdemo.component";
import { OverlaysDemoComponent } from "./demo/view/overlaysdemo.component";
import { MediaDemoComponent } from "./demo/view/mediademo.component";
import { MessagesDemoComponent } from "./demo/view/messagesdemo.component";
import { MiscDemoComponent } from "./demo/view/miscdemo.component";
import { EmptyDemoComponent } from "./demo/view/emptydemo.component";
import { ChartsDemoComponent } from "./demo/view/chartsdemo.component";
import { FileDemoComponent } from "./demo/view/filedemo.component";
import { DocumentationComponent } from "./demo/view/documentation.component";
import { AppMainComponent } from "./app.main.component";
import { AppNotfoundComponent } from "./pages/app.notfound.component";
import { AppErrorComponent } from "./pages/app.error.component";
import { AppAccessdeniedComponent } from "./pages/app.accessdenied.component";
import { AppLoginComponent } from "./pages/app.login.component";
import { InputDemoComponent } from "./demo/view/inputdemo.component";
import { FloatLabelDemoComponent } from "./demo/view/floatlabeldemo.component";
import { InvalidStateDemoComponent } from "./demo/view/invalidstatedemo.component";
import { ButtonDemoComponent } from "./demo/view/buttondemo.component";
import { TableDemoComponent } from "./demo/view/tabledemo.component";
import { ListDemoComponent } from "./demo/view/listdemo.component";
import { TreeDemoComponent } from "./demo/view/treedemo.component";
import { IconsComponent } from "./utilities/icons.component";
import { AppCrudComponent } from "./pages/app.crud.component";
import { AppCalendarComponent } from "./pages/app.calendar.component";
import { AppTimelineDemoComponent } from "./pages/app.timelinedemo.component";
import { AppInvoiceComponent } from "./pages/app.invoice.component";
import { AppHelpComponent } from "./pages/app.help.component";
import { BlocksComponent } from "./blocks/blocks/blocks.component";
import { AuthGuard } from "./guards/auth.guard";
import { LoggedInGuard } from "./guards/loggedin.guard";
import { Roles } from "./models/roles";
import { formsDataRoute } from "./routes/Frontend";
@NgModule({
    imports: [
        RouterModule.forRoot(
            [
                {
                    path: "",
                    redirectTo: "auth",
                    pathMatch: "full",
                    // canActivate: [AuthGuard],
                    data: {
                        userRoles: [], // All User Can Access but must be login
                    },
                },
                {
                    path: "",
                    component: AppMainComponent,
                    children: [
                        { path: "", component: DashboardDemoComponent },
                        {
                            path: "uikit/formlayout",
                            component: FormLayoutDemoComponent,
                        },
                        {
                            path: "uikit/floatlabel",
                            component: FloatLabelDemoComponent,
                        },
                        {
                            path: "uikit/invalidstate",
                            component: InvalidStateDemoComponent,
                        },
                        { path: "uikit/input", component: InputDemoComponent },
                        {
                            path: "uikit/button",
                            component: ButtonDemoComponent,
                        },
                        { path: "uikit/table", component: TableDemoComponent },
                        { path: "uikit/list", component: ListDemoComponent },
                        { path: "uikit/tree", component: TreeDemoComponent },
                        { path: "uikit/panel", component: PanelsDemoComponent },
                        {
                            path: "uikit/overlay",
                            component: OverlaysDemoComponent,
                        },
                        {
                            path: "uikit/menu",
                            loadChildren: () =>
                                import("./demo/view/menus/menus.module").then(
                                    (m) => m.MenusModule
                                ),
                        },
                        { path: "uikit/media", component: MediaDemoComponent },
                        {
                            path: "uikit/message",
                            component: MessagesDemoComponent,
                        },
                        { path: "uikit/misc", component: MiscDemoComponent },
                        {
                            path: "uikit/charts",
                            component: ChartsDemoComponent,
                        },
                        { path: "uikit/file", component: FileDemoComponent },
                        { path: "utilities/icons", component: IconsComponent },
                        { path: "pages/crud", component: AppCrudComponent },
                        {
                            path: "pages/calendar",
                            component: AppCalendarComponent,
                        },
                        {
                            path: "pages/timeline",
                            component: AppTimelineDemoComponent,
                        },
                        {
                            path: "pages/invoice",
                            component: AppInvoiceComponent,
                        },
                        { path: "pages/help", component: AppHelpComponent },
                        { path: "pages/empty", component: EmptyDemoComponent },
                        {
                            path: "documentation",
                            component: DocumentationComponent,
                        },
                        { path: "blocks", component: BlocksComponent },
                        // {
                        //     path: "login",
                        //     canActivate: [LoggedInGuard],
                        //     data: {
                        //         userRoles: [], // All User Can Access but must be login
                        //     },
                        //     loadChildren: () =>
                        //         import("./login/login.module").then(
                        //             (m) => m.LoginModule
                        //         ),
                        // },
                        {
                            path: "project",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/project/project.module"
                                ).then((m) => m.ProjectModule),
                        },
                        {
                            path: "home",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [], // All User Can Access but must be login
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/home/home.module"
                                ).then((m) => m.HomeModule),
                        },
                        {
                            path: "forms",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/mis-forms/mis-forms.module"
                                ).then((m) => m.MisFormsModule),
                        },
                        {
                            path: formsDataRoute,
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_PARTNER_DATA_MANAGER,
                                    Roles.ROLE_PARTNER_DATA_VIEWER,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/form-data/form-data.module"
                                ).then((m) => m.FormDataModule),
                        },
                        {
                            path: "formSettings/form/:formtable",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/form-setting/form-setting.module"
                                ).then((m) => m.FormSettingModule),
                        },
                        {
                            path: "archive",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/archive/archive.module"
                                ).then((m) => m.ArchiveModule),
                        },
                        {
                            path: "partnerSetup",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/partner-setup/partner-setup.module"
                                ).then((m) => m.PartnerSetupModule),
                        },
                        {
                            path: "partnerSetup/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/partner-setup/partner-setup.module"
                                ).then((m) => m.PartnerSetupModule),
                        },
                        {
                            path: "partnerSetupList",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/partner-setup/partner-list/partner-list.module"
                                ).then((m) => m.PartnerListModule),
                        },
                        {
                            path: "taskList",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/task-list/task-list.module"
                                ).then((m) => m.TaskListModule),
                        },
                        {
                            path: "submitletterofinterest",
                            data: {
                                userRoles: [],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/grant-process/grant-process.module"
                                ).then((m) => m.GrantProcessModule),
                        },
                        {
                            path: "grantProcess/:id/:readonly",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_ED,
                                    Roles.ROLE_FINANCE,
                                    Roles.ROLE_PROGRAM_OFFICER,
                                    Roles.ROLE_APPLICANT,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/grant-process/grant-process.module"
                                ).then((m) => m.GrantProcessModule),
                        },
                        {
                            path: "longTermGrant",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_ED,
                                    Roles.ROLE_PROGRAM_OFFICER,
                                    Roles.ROLE_APPLICANT,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/grant-process/long-term-grant/long-term-grant.module"
                                ).then((m) => m.LongTermGrantModule),
                        },
                        {
                            path: "longTermGrant/:id/:readonly",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_ED,
                                    Roles.ROLE_PROGRAM_OFFICER,
                                    Roles.ROLE_APPLICANT,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/grant-process/long-term-grant/long-term-grant.module"
                                ).then((m) => m.LongTermGrantModule),
                        },
                        {
                            path: "longTermGrantApplication",
                            data: {
                                userRoles: [],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/grant-process/long-term-grant/start-long-term-grant.module"
                                ).then((m) => m.StartLongTermGrantModule),
                        },
                        {
                            path: "reportForm/:id/:readonly",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_MEAL,
                                    Roles.ROLE_FINANCE,
                                    Roles.ROLE_ED,
                                    Roles.ROLE_PROGRAM_OFFICER,
                                    Roles.ROLE_PARTNER_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/report-form/report-form.module"
                                ).then((m) => m.ReportFormModule),
                        },
                        {
                            path: "entity",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entities/entities.module"
                                ).then((m) => m.EntitiesModule),
                        },
                        {
                            path: "entity/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entities/create-entities/create-entities.module"
                                ).then((m) => m.CreateEntitiesModule),
                        },
                        {
                            path: "entity/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entities/edit-entities/edit-entities.module"
                                ).then((m) => m.EditEntitiesModule),
                        },
                        {
                            path: "entity/showData/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_PARTNER_DATA_MANAGER,
                                    Roles.ROLE_PARTNER_DATA_VIEWER,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entities/entity-tables/entity-tables.module"
                                ).then((m) => m.EntityTablesModule),
                        },
                        {
                            path: "staging-beneficiary-list",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_PARTNER_DATA_MANAGER,
                                    Roles.ROLE_PARTNER_DATA_VIEWER,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entities/entity-tables/staging-beneficiary-list/staging-beneficiary-list.module"
                                ).then((m) => m.StagingBeneficiaryListModule),
                        },
                        {
                            path: "entity/linkForm/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entities/link-form/link-form.module"
                                ).then((m) => m.LinkFormModule),
                        },
                        {
                            path: "entityView",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entity-views/entity-views.module"
                                ).then((m) => m.EntityViewsModule),
                        },
                        {
                            path: "entityView/create/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entity-views/create-entity-views/create-entity-views.module"
                                ).then((m) => m.CreateEntityViewsModule),
                        },
                        {
                            path: "entityView/edit/:entityId/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entity-views/edit-entity-views/edit-entity-views.module"
                                ).then((m) => m.EditEntityViewsModule),
                        },
                        {
                            path: "entityView/showData/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entity-views/entity-view-table/entity-view-table.module"
                                ).then((m) => m.EntityViewTableModule),
                        },
                        {
                            path: "tags",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_PARTNER_DATA_MANAGER,
                                    Roles.ROLE_PARTNER_DATA_VIEWER,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/tags/tags.module"
                                ).then((m) => m.TagsModule),
                        },
                        {
                            path: "tags/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_PARTNER_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/tags/edit-tags/edit-tags.module"
                                ).then((m) => m.EditTagsModule),
                        },
                        {
                            path: "tagType",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/tag-type/tag-type.module"
                                ).then((m) => m.TagTypeModule),
                        },
                        {
                            path: "tagType/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/tag-type/edit-tag-type/edit-tag-type.module"
                                ).then((m) => m.EditTagTypeModule),
                        },
                        {
                            path: "groups",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/groups/groups.module"
                                ).then((m) => m.GroupsModule),
                        },
                        {
                            path: "group/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/groups/create-group/create-group.module"
                                ).then((m) => m.CreateGroupModule),
                        },
                        {
                            path: "group/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/groups/edit-group/edit-group.module"
                                ).then((m) => m.EditGroupModule),
                        },
                        {
                            path: "users",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/users/users.module"
                                ).then((m) => m.UsersModule),
                        },
                        {
                            path: "mis-users",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/users/users.module"
                                ).then((m) => m.UsersModule),
                        },
                        {
                            path: "user/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/users/create-user/create-user.module"
                                ).then((m) => m.CreateUserModule),
                        },
                        {
                            path: "user/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/users/edit-user/edit-user.module"
                                ).then((m) => m.EditUserModule),
                        },
                        {
                            path: "roles",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/roles/roles.module"
                                ).then((m) => m.RolesModule),
                        },
                        {
                            path: "roles",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/roles/roles.module"
                                ).then((m) => m.RolesModule),
                        },
                        {
                            path: "role/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/roles/create-role/create-role.module"
                                ).then((m) => m.CreateRoleModule),
                        },
                        {
                            path: "role/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/roles/edit-role/edit-role.module"
                                ).then((m) => m.EditRoleModule),
                        },
                        {
                            path: "scheduledTasks",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [Roles.ROLE_SUPER_ADMIN],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/scheduled-tasks/scheduled-tasks.module"
                                ).then((m) => m.ScheduledTasksModule),
                        },
                        {
                            path: "acl-group-mapping-edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [Roles.ROLE_SUPER_ADMIN],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/acl-group-mapping-edit/acl-group-mapping-edit.module"
                                ).then((m) => m.AclGroupMappingEditModule),
                        },
                        {
                            path: "acl-group-mapping-lists",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [Roles.ROLE_SUPER_ADMIN],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/acl-group-mapping-parent/acl-group-mapping-parent.module"
                                ).then((m) => m.AclGroupMappingParentModule),
                        },
                        {
                            path: "acl-group-mapping-lists",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [Roles.ROLE_SUPER_ADMIN],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/acl-group-mapping-lists/acl-group-mapping-lists.module"
                                ).then((m) => m.AclGroupMappingListsModule),
                        },
                        {
                            path: "requestMaps",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [Roles.ROLE_SUPER_ADMIN],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/request-maps/request-maps.module"
                                ).then((m) => m.RequestMapsModule),
                        },
                        {
                            path: "requestMaps/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [Roles.ROLE_SUPER_ADMIN],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/request-maps/create-request-maps/create-request-maps.module"
                                ).then((m) => m.CreateRequestMapsModule),
                        },
                        {
                            path: "requestMaps/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [Roles.ROLE_SUPER_ADMIN],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/request-maps/edit-request-maps/edit-request-maps.module"
                                ).then((m) => m.EditRequestMapsModule),
                        },
                        {
                            path: "messagePage/:type",
                            data: {
                                userRoles: [
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/message-pages/message-pages.module"
                                ).then((m) => m.MessagePagesModule),
                        },
                        {
                            path: "program",
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program/program.module"
                                ).then((m) => m.ProgramModule),
                        },
                        {
                            path: "program/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program/create-program/create-program.module"
                                ).then((m) => m.CreateProgramModule),
                        },
                        {
                            path: "program/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program/edit-program/edit-program.module"
                                ).then((m) => m.EditProgramModule),
                        },
                        {
                            path: "programCategory",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program-category/program-category.module"
                                ).then((m) => m.ProgramCategoryModule),
                        },
                        {
                            path: "programCategory/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program-category/create-program-category/create-program-category.module"
                                ).then((m) => m.CreateProgramCategoryModule),
                        },
                        {
                            path: "programCategory/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program-category/edit-program-category/edit-program-category.module"
                                ).then((m) => m.EditProgramCategoryModule),
                        },
                        {
                            path: "milestones",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/project-milestones/project-milestones.module"
                                ).then((m) => m.ProjectMilestonesModule),
                        },
                        {
                            path: "milestones/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/project-milestones/create-project-milestones/create-project-milestones.module"
                                ).then((m) => m.CreateProjectMilestonesModule),
                        },
                        {
                            path: "milestones/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/project-milestones/edit-project-milestones/edit-project-milestones.module"
                                ).then((m) => m.EditProjectMilestonesModule),
                        },
                        {
                            path: "programPartner",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                    Roles.ROLE_STAFF_DATA_VIEWER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program-partners/program-partners.module"
                                ).then((m) => m.ProgramPartnersModule),
                        },
                        {
                            path: "programPartner/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program-partners/create-program-partners/create-program-partners.module"
                                ).then((m) => m.CreateProgramPartnersModule),
                        },
                        {
                            path: "programPartner/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                    Roles.ROLE_STAFF_DATA_MANAGER,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/program-partners/edit-program-partners/edit-program-partners.module"
                                ).then((m) => m.EditProgramPartnersModule),
                        },
                        {
                            path: "entityViewFilter",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entity-view-filters/entity-view-filters.module"
                                ).then((m) => m.EntityViewFiltersModule),
                        },
                        {
                            path: "entityViewFilter/create/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entity-view-filters/create-entity-view-filters/create-entity-view-filters.module"
                                ).then((m) => m.CreateEntityViewFiltersModule),
                        },
                        {
                            path: "entityViewFilter/edit/:entityViewId/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/entity-view-filters/edit-entity-view-filters/edit-entity-view-filters.module"
                                ).then((m) => m.EditEntityViewFiltersModule),
                        },
                        {
                            path: "dataView",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/data-views/data-views.module"
                                ).then((m) => m.DataViewsModule),
                        },
                        {
                            path: "dataView/create",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/data-views/create-data-views/create-data-views.module"
                                ).then((m) => m.CreateDataViewsModule),
                        },
                        {
                            path: "dataView/edit/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/data-views/edit-data-views/edit-data-views.module"
                                ).then((m) => m.EditDataViewsModule),
                        },
                        {
                            path: "dataView/showData/:id",
                            canActivate: [AuthGuard],
                            data: {
                                userRoles: [
                                    Roles.ROLE_SUPER_ADMIN,
                                    Roles.ROLE_ADMIN,
                                ],
                            },
                            loadChildren: () =>
                                import(
                                    "./mis-components/data-views/data-view-table/data-view-table.module"
                                ).then((m) => m.DataViewTableModule),
                        },
                    ],
                },
                {
                    path: "login",
                    canActivate: [LoggedInGuard],
                    data: {
                        userRoles: [], // All User Can Access but must be login
                    },
                    loadChildren: () =>
                        import("./login/login.module").then(
                            (m) => m.LoginModule
                        ),
                },
                { path: "error", component: AppErrorComponent },
                { path: "access", component: AppAccessdeniedComponent },
                { path: "notfound", component: AppNotfoundComponent },
                // { path: "login", component: AppLoginComponent },
                { path: "**", redirectTo: "/notfound" },
            ],
            { scrollPositionRestoration: "enabled" }
        ),
    ],
    exports: [RouterModule],
})
export class AppRoutingModule {}
