import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MenuwebTagModule } from './tag/tag.module';
import { MenuwebPlatModule } from './plat/plat.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MenuwebTagModule,
        MenuwebPlatModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MenuwebEntityModule {}
