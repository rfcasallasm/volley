/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VolleyTestModule } from '../../../test.module';
import { LetterBookDeleteDialogComponent } from 'app/entities/letter-book/letter-book-delete-dialog.component';
import { LetterBookService } from 'app/entities/letter-book/letter-book.service';

describe('Component Tests', () => {
    describe('LetterBook Management Delete Component', () => {
        let comp: LetterBookDeleteDialogComponent;
        let fixture: ComponentFixture<LetterBookDeleteDialogComponent>;
        let service: LetterBookService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [LetterBookDeleteDialogComponent]
            })
                .overrideTemplate(LetterBookDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LetterBookDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LetterBookService);
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
