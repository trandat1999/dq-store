import {Component, inject} from '@angular/core';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzSpaceComponent, NzSpaceItemDirective} from 'ng-zorro-antd/space';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzFormControlComponent, NzFormDirective, NzFormLayoutType} from 'ng-zorro-antd/form';
import {InputComponent} from '../commons/input/input.component';
import {FormsModule, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {NzRadioComponent, NzRadioGroupComponent} from 'ng-zorro-antd/radio';
import {NzInputDirective} from 'ng-zorro-antd/input';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  imports: [
    NzButtonComponent,
    NzSpaceComponent,
    NzSpaceItemDirective,
    NzRowDirective,
    NzColDirective,
    NzFormDirective,
    InputComponent,
    FormsModule,
    NzSelectComponent,
    NzOptionComponent,
    ReactiveFormsModule,
    NzRadioGroupComponent,
    NzRadioComponent,
    NzInputDirective,
    NzFormControlComponent
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
  test: any = {

  }

  constructor(private message: NzMessageService,
              private notification: NzNotificationService) {}


  private fb = inject(NonNullableFormBuilder);
  validateForm = this.fb.group({
    formLayout: this.fb.control<NzFormLayoutType>('horizontal'),
    fieldA: this.fb.control('', [Validators.required]),
    filedB: this.fb.control('', [Validators.required])
  });

  submitForm(): void {
    if (this.validateForm.valid) {
      console.log('submit', this.validateForm.value);
    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  get isHorizontal(): boolean {
    return this.validateForm.controls.formLayout.value === 'horizontal';
  }
}
