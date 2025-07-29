import { Component, Input } from '@angular/core';
import {NzBreadCrumbComponent, NzBreadCrumbItemComponent} from 'ng-zorro-antd/breadcrumb';
import {RouterLink} from '@angular/router';
import {NgForOf, NgIf} from '@angular/common';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {TranslatePipe} from '@ngx-translate/core';

export interface BreadcrumbItem {
  link?: string;
  name?: string;
}
@Component({
  selector: 'dq-breadcrumb',
  imports: [
    NzBreadCrumbComponent,
    NzBreadCrumbItemComponent,
    RouterLink,
    NgIf,
    NgForOf,
    NzIconDirective,
    TranslatePipe
  ],
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.css'
})
export class BreadcrumbComponent {
  @Input() lang: string
  @Input() items :BreadcrumbItem[] = [];
  @Input() autoGenerate: boolean = false;
  @Input() routeLabel: string = 'breadcrumb';
  @Input() routeLabelFn: (label: string) => string = label => label;
  constructor(
  ) { }
}
