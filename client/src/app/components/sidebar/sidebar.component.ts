import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from "../../services/auth.service";
import {FormService} from "../../services/form.service";
import {ReplacePipe} from "../../replace-pipe";

let misc: any = {
  sidebar_mini_active: true
};

let formsMenu: any = {
  path: '/forms/data',
  title: 'Data',
  type: 'sub',
  icontype: 'ni-single-copy-04 text-pink',
  isCollapsed: true,
  children: []
}

let formSettingsMenu: any = {
  path: 'formSettings/form',
  title: 'Form Settings',
  type: 'sub',
  isCollapsed: true,
  children: []
}

export interface RouteInfo {
  path: string;
  title: string;
  type: string;
  icontype: string;
  collapse?: string;
  isCollapsed?: boolean;
  isCollapsing?: any;
  children?: ChildrenItems[];
}

export interface ChildrenItems {
  path: string;
  title: string;
  type?: string;
  collapse?: string;
  children?: ChildrenItems2[];
  isCollapsed?: boolean;
}

export interface ChildrenItems2 {
  path?: string;
  title?: string;
  type?: string;
}

// Menu Items
export const ROUTES: RouteInfo[] = [
  {
    path: '/',
    title: 'Home',
    type: 'link',
    icontype: 'fas fa-home',
  },
  formsMenu,
  {
    path: '/widgets',
    title: 'Reports',
    type: 'sub',
    icontype: 'ni-books text-pink',
    isCollapsed: true,
    children: [
      {path: 'dashboard', title: 'Farmer Report', type: 'link'},
      {path: 'alternative', title: 'Trends', type: 'link'}
    ]
  },
  {
    path: '/widgets',
    title: 'Admin',
    type: 'sub',
    icontype: 'ni-badge text-default',
    isCollapsed: true,
    children: [
      {path: 'dashboard', title: 'Create Packages', type: 'link'},
      {path: 'alternative', title: 'Assign Packages', type: 'link'}
    ]
  },
  {
    path: '/',
    title: 'Settings',
    type: 'sub',
    icontype: 'ni-settings text-primary',
    isCollapsed: true,
    children: [
      {path: 'forms', title: 'Forms', type: 'link'},
      formSettingsMenu,
      {path: 'project', title: 'Project', type: 'link'},
      {path: 'entity', title: 'Entities', type: 'link'},
      {path: 'entityView', title: 'Entity Views', type: 'link'},
    ]
  },
  {
    path: '/widgets',
    title: 'User',
    type: 'sub',
    icontype: 'ni-single-02 text-green',
    isCollapsed: true,
    children: [
      {path: 'dashboard', title: 'Roles', type: 'link'},
      {path: 'alternative', title: 'Groups', type: 'link'}
    ]
  },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  public menuItems: any[];
  public isCollapsed = true;

  constructor(private router: Router, public authService: AuthService, private formService: FormService) {
  }

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
    this.router.events.subscribe(event => {
      this.isCollapsed = true;
    });
    this.formService.getEnabledForms().subscribe(data => {
      for (let form of data) {
        let formObject = {};
        let formSettingObject = {};
        formObject['title'] = new ReplacePipe().transform(form.displayName, '_', ' ').toUpperCase();
        formObject['path'] = form.name.toString();
        formObject['type'] = 'link';
        formsMenu.children.push(formObject);

        formSettingObject['title'] = new ReplacePipe().transform(form.displayName, '_', ' ').toUpperCase();
        formSettingObject['path'] = form.name.toString();
        formSettingObject['type'] = 'link';
        formSettingsMenu.children.push(formSettingObject);
      }
    }, error => console.log(error));
  }

  onMouseEnterSidenav() {
    if (!document.body.classList.contains('g-sidenav-pinned')) {
      document.body.classList.add('g-sidenav-show');
    }
  }

  onMouseLeaveSidenav() {
    if (!document.body.classList.contains('g-sidenav-pinned')) {
      document.body.classList.remove('g-sidenav-show');
    }
  }

  minimizeSidebar() {
    const sidenavToggler = document.getElementsByClassName(
      'sidenav-toggler'
    )[0];
    const body = document.getElementsByTagName('body')[0];
    if (body.classList.contains('g-sidenav-pinned')) {
      misc.sidebar_mini_active = true;
    } else {
      misc.sidebar_mini_active = false;
    }
    if (misc.sidebar_mini_active === true) {
      body.classList.remove('g-sidenav-pinned');
      body.classList.add('g-sidenav-hidden');
      sidenavToggler.classList.remove('active');
      misc.sidebar_mini_active = false;
    } else {
      body.classList.add('g-sidenav-pinned');
      body.classList.remove('g-sidenav-hidden');
      sidenavToggler.classList.add('active');
      misc.sidebar_mini_active = true;
    }
  }
}
