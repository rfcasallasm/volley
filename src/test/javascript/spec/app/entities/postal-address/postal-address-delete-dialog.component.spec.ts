/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VolleyTestModule } from '../../../test.module';
import { PostalAddressDeleteDialogComponent } from 'app/entities/postal-address/postal-address-delete-dialog.component';
import { PostalAddressService } from 'app/entities/postal-address/postal-address.service';

describe('Component Tests', () => {
    describe('PostalAddress Management Delete Component', () => {
        let comp: PostalAddressDeleteDialogComponent;
        let fixture: ComponentFixture<PostalAddressDeleteDialogComponent>;
        let service: PostalAddressService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [PostalAddressDeleteDialogComponent]
            })
                .overrideTemplate(PostalAddressDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PostalAddressDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PostalAddressService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
