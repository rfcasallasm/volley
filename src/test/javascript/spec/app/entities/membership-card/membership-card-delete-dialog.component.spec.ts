/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VolleyTestModule } from '../../../test.module';
import { MembershipCardDeleteDialogComponent } from 'app/entities/membership-card/membership-card-delete-dialog.component';
import { MembershipCardService } from 'app/entities/membership-card/membership-card.service';

describe('Component Tests', () => {
    describe('MembershipCard Management Delete Component', () => {
        let comp: MembershipCardDeleteDialogComponent;
        let fixture: ComponentFixture<MembershipCardDeleteDialogComponent>;
        let service: MembershipCardService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [MembershipCardDeleteDialogComponent]
            })
                .overrideTemplate(MembershipCardDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MembershipCardDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembershipCardService);
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
