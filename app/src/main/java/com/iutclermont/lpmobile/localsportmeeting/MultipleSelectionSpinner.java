package com.iutclermont.lpmobile.localsportmeeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.iutclermont.lpmobile.localsportmeeting.backend.categorieApi.model.Categorie;


public class MultipleSelectionSpinner extends Spinner implements
        OnMultiChoiceClickListener {


    private List<Categorie> listCategorie = null;

    private List<String> listCategorieAfficher = new ArrayList<String>();

    private SlidingTabsBasicFragment tabsFragment = null;

    boolean[] mSelection = null;

    AlertDialog dialogCategorie;

    ArrayAdapter<String> simple_adapter;

    public MultipleSelectionSpinner(Context context) {
        super(context);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public MultipleSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item);
        simple_adapter.clear();
        simple_adapter.add("Categorie");
        super.setAdapter(simple_adapter);
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        List<Categorie> categSelected = new ArrayList<Categorie>();
        if (which == 0) {
            mSelection[0] = isChecked;
            for (int i = 1; i < mSelection.length;++i) {
                mSelection[i] = isChecked;
            }
            if (isChecked) {
                categSelected = listCategorie;
            }
        }
        else {
            mSelection[0] = false;
            if (mSelection != null && which < mSelection.length) {
                mSelection[which] = isChecked;
            } else {
                throw new IllegalArgumentException(
                        "Argument 'which' is out of bounds.");
            }

            for (int i=1;i<mSelection.length;++i) {
                if (mSelection[i]) {
                    categSelected.add(listCategorie.get(i-1));
                }
            }
            if (categSelected.size() == listCategorie.size()) {
                mSelection[0] = true;
                for (int i = 1; i < mSelection.length;++i) {
                    mSelection[i] = true;
                }
            }
        }
        dialogCategorie.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(getListCategorieToString(), mSelection, this);
        dialogCategorie = builder.create();
        dialogCategorie.show();
        tabsFragment.setListCategSelection(categSelected);
    }

    @Override
    public boolean performClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(getListCategorieToString(), mSelection, this);
        dialogCategorie = builder.create();
        dialogCategorie.show();
        return true;
    }

    private String[] getListCategorieToString() {
        String[] listItems = new String[listCategorieAfficher.size()];
        int i=0;
        for (String s : listCategorieAfficher) {
            listItems[i] = s;
            ++i;
        }
        return listItems;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems (List<Categorie> items) {
        listCategorie = items;
        listCategorieAfficher = new ArrayList<String>();
        listCategorieAfficher.add("Toutes");
        for (Categorie c : listCategorie) {
            listCategorieAfficher.add(c.getLibelle());
        }

        mSelection = new boolean[listCategorie.size()+1];
        Arrays.fill(mSelection, true);
        mSelection[0] = true;

    }

    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<String>();
        for (int i = 0; i < listCategorie.size(); ++i) {
            if (mSelection[i]) {
                selection.add(listCategorie.get(i).getLibelle());
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<Integer>();
        for (int i = 0; i < listCategorie.size(); ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    public void setTabsFragment (SlidingTabsBasicFragment tabsFragment) {
        this.tabsFragment = tabsFragment;
        tabsFragment.setListCategSelection(listCategorie);
    }
}
