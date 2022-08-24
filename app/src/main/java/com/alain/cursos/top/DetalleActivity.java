package com.alain.cursos.top;

/* *
 * Project: MD Top from com.alain.cursos.top
 * Created by Alain Nicolás Tello on 10/11/2019 at 06:21 PM
 * All rights reserved 2020.
 * Course Material Design and Theming Professional for Android
 * More info: https://www.udemy.com/course/material-design-theming-diseno-profesional-para-android/
 * Cursos Android ANT
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 21;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.etNombre)
    TextInputEditText etNombre;
    @BindView(R.id.etApellidos)
    TextInputEditText etApellidos;
    @BindView(R.id.etFechaNacimiento)
    TextInputEditText etFechaNacimiento;
    @BindView(R.id.etEdad)
    TextInputEditText etEdad;
    @BindView(R.id.etEstatura)
    TextInputEditText etEstatura;
    @BindView(R.id.etOrden)
    TextInputEditText etOrden;
    @BindView(R.id.etLugarNacimiento)
    TextInputEditText etLugarNacimiento;
    @BindView(R.id.etNotas)
    TextInputEditText etNotas;
    @BindView(R.id.containerMain)
    NestedScrollView containerMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tilNombre)
    TextInputLayout tilNombre;
    @BindView(R.id.tilApellidos)
    TextInputLayout tilApellidos;
    @BindView(R.id.tilEstatura)
    TextInputLayout tilEstatura;
    // nueva vista
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.imgCover)
    AppCompatImageView imgCover;
    @BindView(R.id.imgFoto)
    CircleImageView imgFoto;

    private Artista mArtista;
    private MenuItem mMenuItem;
    private boolean mIsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        ButterKnife.bind(this);

        configArtista(getIntent());
        configActionBar();
        configImageView(mArtista.getFotoUrl());
    }

    private void configArtista(Intent intent) {
        getArtist(intent.getLongExtra(Artista.ID, 0));

        etNombre.setText(mArtista.getNombre());
        etApellidos.setText(mArtista.getApellidos());
        etFechaNacimiento.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                .format(mArtista.getFechaNacimiento()));
        etEdad.setText(getEdad(mArtista.getFechaNacimiento()));
        etEstatura.setText(String.valueOf(mArtista.getEstatura()));
        etOrden.setText(String.valueOf(mArtista.getOrden()));
        etLugarNacimiento.setText(mArtista.getLugarNacimiento());
        etNotas.setText(mArtista.getNotas());
    }

    private void getArtist(long id) {
        mArtista = SQLite
                .select()
                .from(Artista.class)
                .where(Artista_Table.id.is(id))
                .querySingle();
    }

    private String getEdad(long fechaNacimiento) {
        long time = Calendar.getInstance().getTimeInMillis() / 1000 - fechaNacimiento / 1000;
        final int years = Math.round(time) / 31536000;
        return String.valueOf(years);
    }

    private void configActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbarLayout.setExpandedTitleColor(Color.WHITE);
        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            /*
            toolbarLayout.setTitle("appBarLayout = " + appBarLayout.getTotalScrollRange());
            etNombre.setText("verticalOffset = " + verticalOffset);

            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()){
                toolbar.getNavigationIcon().setTint(Color.BLACK);
            } else {
                toolbar.getNavigationIcon().setTint(Color.WHITE);
            }*/
            if (verticalOffset == 0) {
                tvName.setVisibility(View.VISIBLE);
            } else {
                tvName.setVisibility(View.GONE);
            }

            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                float percentage = Math.abs((float) Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange() - 1);
                int colorValue = (int) (percentage * 255);
                if (toolbar.getNavigationIcon() != null) {
                    toolbar.getNavigationIcon().setTint(Color.rgb(colorValue, colorValue, colorValue));
                }
                /*etApellidos.setText("verticalScoll/appBar = " + (float)Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange());
                etLugarNacimiento.setText("Porcentaje = " + percentage);
                etNotas.setText("ColorValue = " + colorValue);*/
            }
        });

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop();

        Glide.with(this)
                .load(R.drawable.img_cover_material_design)
                .apply(options)
                .into(imgCover);

        configTitle();
    }

    private void configTitle() {
        toolbarLayout.setTitle(mArtista.getNombreCompleto());
        tvName.setText(mArtista.getNombreCompleto());
    }

    private void configImageView(String fotoUrl) {
        if (fotoUrl != null) {
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop();

            Glide.with(this)
                    .load(fotoUrl)
                    .apply(options)
                    .into(imgFoto);
        } else {
            imgFoto.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_photo_size_select_actual));
        }

        mArtista.setFotoUrl(fotoUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        mMenuItem = menu.findItem(R.id.action_save);
        mMenuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                super.onBackPressed();
                break;
            case R.id.action_save:
                saveOrEdit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_PHOTO_PICKER) {
                savePhotoUrlArtist(data.getDataString());
            }
        }
    }

    private void savePhotoUrlArtist(String fotoUrl) {
        try {
            mArtista.setFotoUrl(fotoUrl);
            mArtista.update();
            configImageView(fotoUrl);
            showMessage(R.string.detalle_message_update_success);
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(R.string.detalle_message_update_fail);
        }
    }

    @OnClick(R.id.fab)
    public void saveOrEdit() {
        if (mIsEdit) {
            if (validateFields() && etNombre.getText() != null && etApellidos.getText() != null &&
                    etEstatura.getText() != null && etLugarNacimiento.getText() != null &&
                    etNotas.getText() != null) {
                mArtista.setNombre(etNombre.getText().toString().trim());
                mArtista.setApellidos(etApellidos.getText().toString().trim());
                mArtista.setEstatura(Short.valueOf(etEstatura.getText().toString().trim()));
                mArtista.setLugarNacimiento(etLugarNacimiento.getText().toString().trim());
                mArtista.setNotas(etNotas.getText().toString().trim());

                try {
                    mArtista.update();
                    configTitle();
                    showMessage(R.string.detalle_message_update_success);
                    Log.i("DBFlow", "Inserción correcta de datos.");

                    fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_edit));
                    enableUIElements(false);
                    mIsEdit = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage(R.string.detalle_message_update_fail);
                    Log.i("DBFlow", "Error al insertar datos.");
                }
            }
        } else {
            mIsEdit = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_account_check));
            enableUIElements(true);
        }
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (etEstatura.getText() != null && (etEstatura.getText().toString().trim().isEmpty() ||
                Integer.valueOf(etEstatura.getText().toString().trim()) < getResources().getInteger(R.integer.estatura_min))) {
            tilEstatura.setError(getString(R.string.addArtist_error_estaturaMin));
            tilEstatura.requestFocus();
            isValid = false;
        } else {
            tilEstatura.setError(null);
        }
        if (etApellidos.getText() != null && etApellidos.getText().toString().trim().isEmpty()) {
            tilApellidos.setError(getString(R.string.addArtist_error_required));
            tilApellidos.requestFocus();
            isValid = false;
        } else {
            tilApellidos.setError(null);
        }
        if (etNombre.getText() != null && etNombre.getText().toString().trim().isEmpty()) {
            tilNombre.setError(getString(R.string.addArtist_error_required));
            tilNombre.requestFocus();
            isValid = false;
        } else {
            tilNombre.setError(null);
        }

        return isValid;
    }

    private void enableUIElements(boolean enable) {
        etNombre.setEnabled(enable);
        etApellidos.setEnabled(enable);
        etFechaNacimiento.setEnabled(enable);
        etEstatura.setEnabled(enable);
        etLugarNacimiento.setEnabled(enable);
        etNotas.setEnabled(enable);

        mMenuItem.setVisible(enable);
        appBar.setExpanded(!enable);
        containerMain.setNestedScrollingEnabled(!enable);
    }

    @OnClick(R.id.etFechaNacimiento)
    public void onSetFecha() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setSelection(mArtista.getFechaNacimiento());
        builder.setTheme(R.style.PickerDialogCut);

        CalendarConstraints.Builder constraints = new CalendarConstraints.Builder();
        constraints.setOpenAt(mArtista.getFechaNacimiento());
        builder.setCalendarConstraints(constraints.build());

        MaterialDatePicker<?> picker = builder.build();
        picker.addOnPositiveButtonClickListener(selection -> {
            etFechaNacimiento.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                    .format(selection));
            mArtista.setFechaNacimiento((Long) selection);
            etEdad.setText(getEdad((Long) selection));
        });
        picker.show(getSupportFragmentManager(), picker.toString());
    }


    private void showMessage(int resource) {
        Snackbar.make(containerMain, resource, Snackbar.LENGTH_SHORT).show();
    }

    @OnClick({R.id.imgDeleteFoto, R.id.imgFromGallery, R.id.imgFromUrl})
    public void photoHandler(View view) {
        switch (view.getId()) {
            case R.id.imgDeleteFoto:
                //AlertDialog.Builder builder = new AlertDialog.Builder(this)
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                        .setTitle(R.string.detalle_dialogDelete_title)
                        .setMessage(String.format(Locale.ROOT,
                                getString(R.string.detalle_dialogDelete_message),
                                mArtista.getNombreCompleto()))
                        .setPositiveButton(R.string.label_dialog_delete, (dialogInterface, i) ->
                                savePhotoUrlArtist(null))
                        .setNegativeButton(R.string.label_dialog_cancel, null);
                builder.show();
                break;
            case R.id.imgFromGallery:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent,
                        getString(R.string.detalle_chooser_title)), RC_PHOTO_PICKER);
                break;
            case R.id.imgFromUrl:
                showAddPhotoDialog();
                break;
        }
    }

    private void showAddPhotoDialog() {
        final EditText etFotoUrl = new EditText(this);

        //AlertDialog.Builder builder = new AlertDialog.Builder(this)
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.addArtist_dialogUrl_title)
                .setPositiveButton(R.string.label_dialog_add, (dialogInterface, i) ->
                        savePhotoUrlArtist(etFotoUrl.getText().toString().trim()))
                .setNegativeButton(R.string.label_dialog_cancel, null);
        builder.setView(etFotoUrl);
        builder.show();
    }

}
