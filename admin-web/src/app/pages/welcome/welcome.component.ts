import { Component } from '@angular/core';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzSpaceComponent, NzSpaceItemDirective} from 'ng-zorro-antd/space';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  imports: [
    NzButtonComponent,
    NzSpaceComponent,
    NzSpaceItemDirective,
    NzRowDirective,
    NzColDirective
  ],
  styleUrl: './welcome.component.css'
})
export class WelcomeComponent {
  createMessage(type: string): void {
    this.message.create(type, `This is a message of ${type}`,{nzDuration: 0});
  }

  createNotification(type: string): void {
    this.notification.create(
      type,
      'Notification Title',
      'This is the content of the notification. This is the content of the notification. This is the content of the notification.',
      {nzDuration: 0}
    );
  }

  constructor(private message: NzMessageService,
              private notification: NzNotificationService) {}
}
